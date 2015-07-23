package sc.yhy.annotation.injection;

import java.io.FileInputStream;

public// 存放文件信息
class MultipartFileStream {
	private String fileName;
	private Integer length;
	private FileInputStream fileInputStream;

	public MultipartFileStream(String fileName, Integer length, FileInputStream fileInputStream) {
		this.fileName = fileName;
		this.length = length;
		this.fileInputStream = fileInputStream;
	}

	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
}
