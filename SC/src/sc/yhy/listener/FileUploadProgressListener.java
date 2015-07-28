package sc.yhy.listener;

import java.text.DecimalFormat;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import sc.yhy.annotation.Constant;

/**
 * 上传文件进度监听
 * 
 * @author YHY
 *
 */
public class FileUploadProgressListener implements ProgressListener {
	private HttpSession session;

	public FileUploadProgressListener(HttpSession session) {
		this.session = session;
	}

	private DecimalFormat df = new DecimalFormat("#.##");

	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		Double br = Double.parseDouble(pBytesRead + "");
		Double cl = Double.parseDouble(pContentLength + "");
		String bfb = df.format(((br / cl) * 100));
		this.session.setAttribute(Constant.UPLOAD_PROGRESS_PERCENT, bfb);
		System.out.println("处理了 " + bfb + "  正在处理 " + pItems);
	}
}
