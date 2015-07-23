package sc.yhy.annotation.injection;

import java.io.File;
import java.io.InputStream;

/**
 * 装配上传文件流
 * 
 * @author YHY
 *
 */
public class MultipartFileStream {
	private String fileName;
	private Long size;
	private InputStream inputStream;
	private File storeLocation;

	public MultipartFileStream(String fileName, Long size, InputStream inputStream, File storeLocation) {
		this.fileName = fileName;
		this.size = size;
		this.inputStream = inputStream;
		this.storeLocation = storeLocation;
	}

	public MultipartFileStream(String fileName, InputStream inputStream) {
		this.fileName = fileName;
		this.inputStream = inputStream;
	}

	public File getStoreLocation() {
		return storeLocation;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public Long getSize() {
		return size;
	}
}
