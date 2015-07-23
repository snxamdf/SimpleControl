package sc.yhy.annotation.listener;

import java.text.DecimalFormat;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

public class FileUploadProgressListener implements ProgressListener {
	private HttpSession session;

	public FileUploadProgressListener(HttpSession session) {
		super();
		this.session = session;
		//System.out.println("Progress Listened!");
	}

	private DecimalFormat df = new DecimalFormat("#.##");

	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		Double br = Double.parseDouble(pBytesRead + "");
		Double cl = Double.parseDouble(pContentLength + "");
		String bfb = df.format(((br / cl) * 100));
		this.session.setAttribute("upload_progress_percent", bfb);
		//System.out.println("处理了 " + bfb + "  正在处理 " + pItems);
	}
}
