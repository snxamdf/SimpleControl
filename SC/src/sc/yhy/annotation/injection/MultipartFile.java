package sc.yhy.annotation.injection;

public class MultipartFile {
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
}
