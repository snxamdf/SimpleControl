package sc.yhy.annotation.injection;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sc.yhy.listener.FileUploadProgressListener;

/**
 * 上传文件对像
 * 
 * @author YHY
 *
 */
public class MultipartFile {
	private ServletFileUpload upload;
	private MultipartFileStream[] multipartFileStream;
	private int index;

	public MultipartFile() {
		multipartFileStream = new MultipartFileStream[1];
	}

	public MultipartFile(int size) {
		multipartFileStream = new MultipartFileStream[size];
	}

	public void setMultipartFileStream(MultipartFileStream multipartFileStream) {
		this.multipartFileStream[index] = multipartFileStream;
		index++;
	}

	public MultipartFileStream getMultipartFileStream() {
		return multipartFileStream != null && multipartFileStream.length > 0 ? multipartFileStream[0] : null;
	}

	public MultipartFileStream getMultipartFileStream(int index) {
		return multipartFileStream[0];
	}

	public MultipartFileStream[] getMultipartFilesStream() {

		return multipartFileStream;
	}

	public void parseRequest(HttpServletRequest request) {
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> list = this.upload.parseRequest(request);
				for (FileItem item : list) {
					DiskFileItem diskItem = (DiskFileItem) item;
					Long size = diskItem.getSize();
					if (size > 0) {
						// 获取表单的属性名字
						String name = diskItem.getFieldName();
						// 如果获取的 表单信息是普通的 文本 信息
						if (diskItem.isFormField()) {
							// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
							String value = diskItem.getString();
							request.setAttribute(name, value);
						} else {
							// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
							// 获取路径名
							String value = diskItem.getName();
							// 索引到最后一个反斜杠
							int start = value.lastIndexOf("\\");
							// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
							String filename = value.substring(start + 1);
							request.setAttribute(name, filename);
							MultipartFileStream multipartFileStream = new MultipartFileStream(filename, size, diskItem.getInputStream(), diskItem.getStoreLocation());
							this.setMultipartFileStream(multipartFileStream);
						}
					}
				}
			} catch (FileUploadException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setUpload(ServletFileUpload upload) {
		this.upload = upload;
	}

	public void setProgressListener(boolean bool, HttpServletRequest request) {
		ProgressListener pListener = upload.getProgressListener();
		if (pListener == null && bool) {
			this.upload.setProgressListener(new FileUploadProgressListener(request.getSession()));
		} else if (!bool) {
			this.upload.setProgressListener(null);
		}

	}
}
