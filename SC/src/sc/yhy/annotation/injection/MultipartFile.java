package sc.yhy.annotation.injection;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class MultipartFile {
	private HttpServletRequest request;

	public MultipartFile(HttpServletRequest request) {
		try {
			this.request = request;
			this.processRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void processRequest() throws ServletException, IOException {
		// 读取请求Body
		byte[] body = readBody(request);
		// 取得所有Body内容的字符串表示
		String textBody = new String(body, "ISO-8859-1");
		// 取得上传的文件名称
		String fileName = getFileName(textBody);
		// 取得文件开始与结束位置
		Position p = getFilePosition(request, textBody);
		// 输出至文件
		writeTo(fileName, body, p);
	}

	// 构造类
	class Position {

		int begin;
		int end;

		public Position(int begin, int end) {
			this.begin = begin;
			this.end = end;
		}
	}

	private byte[] readBody(HttpServletRequest request) throws IOException {
		// 获取请求文本字节长度
		int formDataLength = request.getContentLength();
		// 取得ServletInputStream输入流对象
		DataInputStream dataStream = new DataInputStream(request.getInputStream());
		byte body[] = new byte[formDataLength];
		int totalBytes = 0;
		while (totalBytes < formDataLength) {
			int bytes = dataStream.read(body, totalBytes, formDataLength);
			totalBytes += bytes;
		}
		dataStream.close();
		return body;
	}

	private Position getFilePosition(HttpServletRequest request, String textBody) throws IOException {
		// 取得文件区段边界信息
		String contentType = request.getContentType();
		String boundaryText = contentType.substring(contentType.lastIndexOf("=") + 1, contentType.length());
		// 取得实际上传文件的气势与结束位置
		int pos = textBody.indexOf("filename=\"");
		pos = textBody.indexOf("\n", pos) + 1;
		pos = textBody.indexOf("\n", pos) + 1;
		pos = textBody.indexOf("\n", pos) + 1;
		int boundaryLoc = textBody.indexOf(boundaryText, pos) - 4;
		int begin = ((textBody.substring(0, pos)).getBytes("ISO-8859-1")).length;
		int end = ((textBody.substring(0, boundaryLoc)).getBytes("ISO-8859-1")).length;

		return new Position(begin, end);
	}

	private String getFileName(String requestBody) {
		String fileName = requestBody.substring(requestBody.indexOf("filename=\"") + 10);
		fileName = fileName.substring(0, fileName.indexOf("\n"));
		fileName = fileName.substring(fileName.indexOf("\n") + 1, fileName.indexOf("\""));

		return fileName;
	}

	private void writeTo(String fileName, byte[] body, Position p) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream("e:/workspace/" + fileName);
		fileOutputStream.write(body, p.begin, (p.end - p.begin));
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	private void tempFile() throws IOException {
		File f = new File("D:\\temp", "aaa.txt");
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			fos = new FileOutputStream(f);

			byte[] b = new byte[1024];
			int n = 0;

			while ((n = is.read(b)) != -1) {
				fos.write(b, 0, n);
			}

		} finally {
			try {
				if (fos != null)
					fos.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
