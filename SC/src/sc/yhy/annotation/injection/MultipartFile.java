package sc.yhy.annotation.injection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
		this.fileItem = fileItem;
	}

	public List<FileItem> parseRequest(HttpServletRequest request) throws FileUploadException {
		return parseRequest(new ServletRequestContext(request));
	}

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
					final String fileName = item.getFieldName();
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

	// public void parseRequest(HttpServletRequest request) {
	//
	// if (ServletFileUpload.isMultipartContent(request)) {
	// try {
	// List<FileItem> list = this.upload.parseRequest(request);
	// for (FileItem item : list) {
	// DiskFileItem diskItem = (DiskFileItem) item;
	// Long size = diskItem.getSize();
	// if (size > 0) {
	// // 获取表单的属性名字
	// String name = diskItem.getFieldName();
	// // 如果获取的 表单信息是普通的 文本 信息
	// if (diskItem.isFormField()) {
	// // 获取请求字段值
	// String value = diskItem.getString();
	// request.setAttribute(name, value);
	// } else {
	// // 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
	// // 获取路径名
	// String value = diskItem.getName();
	// // 索引到最后一个反斜杠
	// int start = value.lastIndexOf("\\");
	// // 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
	// String filename = value.substring(start + 1);
	// request.setAttribute(name, filename);
	// MultipartFileStream multipartFileStream = new
	// MultipartFileStream(filename, size, diskItem.getInputStream(),
	// diskItem.getStoreLocation());
	// this.setMultipartFileStream(multipartFileStream);
	// }
	// }
	// }
	// } catch (FileUploadException | IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	public void setUpload(ServletFileUpload upload) {
		this.upload = upload;
	}

	public void setProgressListener(boolean bool, HttpServletRequest request) {
		if (upload != null) {
			ProgressListener pListener = upload.getProgressListener();
			if (pListener == null && bool) {
				this.upload.setProgressListener(new FileUploadProgressListener(request.getSession()));
			} else if (!bool) {
				this.upload.setProgressListener(null);
			}
		}
	}
}
