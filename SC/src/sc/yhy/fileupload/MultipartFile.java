package sc.yhy.fileupload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.fileupload.util.Streams;

import sc.yhy.listener.FileUploadProgressListener;
import sc.yhy.servlet.HttpRequest;

/**
 * 上传文件对像
 * 
 * @author YHY
 *
 */
public final class MultipartFile extends FileUpload {
	private ServletFileUpload upload;
	private List<FileItem> fileItem;
	public HttpRequest httpRequest;

	public void setHttpRequest(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public List<FileItem> getFileItem() {
		return fileItem;
	}

	public void setFileItem(List<FileItem> fileItem) {
		List<FileItem> items = new ArrayList<FileItem>();
		for (FileItem item : fileItem) {
			if (!item.isFormField() && item.getSize() > 0) {
				items.add(item);
			}
		}
		this.fileItem = items;
	}

	/**
	 * 解析request上传文件
	 */
	public List<FileItem> parseRequest(HttpServletRequest request) throws FileUploadException {
		return parseRequest(new ServletRequestContext(request));
	}

	/**
	 * 解析request上传文件
	 */
	public List<FileItem> parseRequest(RequestContext ctx) throws FileUploadException {
		if (isMultipartContent(ctx)) {
			List<FileItem> items = new ArrayList<FileItem>();
			boolean successful = false;
			try {
				FileItemIterator iter = getItemIterator(ctx);
				FileItemFactory fac = upload.getFileItemFactory();
				if (fac == null) {
					throw new NullPointerException("No FileItemFactory has been set.");
				}
				while (iter.hasNext()) {
					final FileItemStream item = iter.next();
					final String fileName = item.getName();
					FileItem fileItem = fac.createItem(item.getFieldName(), item.getContentType(), item.isFormField(), fileName);
					items.add(fileItem);
					try {
						Streams.copy(item.openStream(), fileItem.getOutputStream(), true);
					} catch (FileUploadIOException e) {
						throw (FileUploadException) e.getCause();
					} catch (IOException e) {
						e.printStackTrace();
					}
					final FileItemHeaders fih = item.getHeaders();
					fileItem.setHeaders(fih);
				}
				successful = true;
				return items;
			} catch (FileUploadIOException e) {
				throw (FileUploadException) e.getCause();
			} catch (IOException e) {
				throw new FileUploadException(e.getMessage(), e);
			} finally {
				if (!successful) {
					for (FileItem fileItem : items) {
						try {
							fileItem.delete();
						} catch (Throwable e) {
							// ignore it
						}
					}
				}
			}
		}
		return null;
	}

	public boolean isMultipart(HttpServletRequest request) {
		return (request != null && ServletFileUpload.isMultipartContent(request));
	}

	public void setUpload(ServletFileUpload upload) {
		this.upload = upload;
	}

	public void setProgressListener(HttpSession session) {
		if (upload != null) {
			ProgressListener pListener = upload.getProgressListener();
			if (pListener == null) {
				this.upload.setProgressListener(new FileUploadProgressListener(session));
			}
		}
	}
}
