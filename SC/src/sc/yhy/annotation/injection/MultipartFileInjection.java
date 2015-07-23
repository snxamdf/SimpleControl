package sc.yhy.annotation.injection;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import sc.yhy.annotation.Constant;

public class MultipartFileInjection {
	private HttpServletRequest request;

	public MultipartFileInjection(HttpServletRequest request) {
		try {
			this.request = request;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected MultipartFile processRequest() throws ServletException, IOException {
		// 读取请求Body
		byte[] body = this.readBody(request);
		if (body.length > 0) {
			// 取得所有Body内容的字符串表示
			String textBody = new String(body, Constant.ISO88591);
			// 取得上传的文件名称,取得文件开始与结束位置
			List<Position> poListFile = this.getFileName(textBody);
			MultipartFile multipartFile = this.fileStream(body, poListFile);
			return multipartFile;
		}
		return new MultipartFile();
	}

	// 取得实际上传文件的起始与结束位置,和文件名
	private List<Position> getFileName(String textBody) throws UnsupportedEncodingException {
		List<Position> poList = new ArrayList<Position>(10);
		String fileName = null, contentType = request.getContentType();
		String boundaryText = contentType.substring(contentType.lastIndexOf("=") + 1, contentType.length());
		String tmpTextBody = new String(textBody);
		int pos, boundaryLoc, begin, end;
		String regEx = "filename=\"([\\s\\S]*?)\"";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(tmpTextBody);
		while (mat.find()) {
			fileName = mat.group(1);
			fileName = new String(fileName.getBytes(Constant.ISO88591), Constant.UTF8);
			if (fileName != null && !"".equals(fileName)) {
				pos = tmpTextBody.indexOf(mat.group());
				pos = tmpTextBody.indexOf("\n", pos) + 1;
				pos = tmpTextBody.indexOf("\n", pos) + 1;
				pos = tmpTextBody.indexOf("\n", pos) + 1;
				boundaryLoc = tmpTextBody.indexOf(boundaryText, pos) - 4;
				begin = ((tmpTextBody.substring(0, pos)).getBytes(Constant.ISO88591)).length;
				end = ((tmpTextBody.substring(0, boundaryLoc)).getBytes(Constant.ISO88591)).length;
				Position position = new Position(fileName, begin, end);
				poList.add(position);
			}
		}
		return poList;
	}

	/**
	 * 获取文件流
	 * 
	 * @param body
	 * @param poListFile
	 * @throws IOException
	 */
	private MultipartFile fileStream(byte[] body, List<Position> poListFile) throws IOException {
		MultipartFile multipartFile = new MultipartFile(poListFile.size());
		for (Position position : poListFile) {
			File file = new File("e:/upload/" + position.fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(body, position.begin, (position.end - position.begin));
			MultipartFileStream multipartFileStream = new MultipartFileStream(position.fileName, (position.end - position.begin), fileInputStream);
			multipartFile.setMultipartFileStream(multipartFileStream);
		}
		return multipartFile;
	}

	private byte[] readBody(HttpServletRequest request) throws IOException {
		// 获取请求文本字节长度
		int formDataLength = request.getContentLength();
		if (formDataLength >= 0) {
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
		return new byte[] {};
	}
}
//存放文件流位置和名称
class Position {
	String fileName;
	int begin;
	int end;

	public Position(String fileName, int begin, int end) {
		this.fileName = fileName;
		this.begin = begin;
		this.end = end;
	}

	public Position(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}
}
