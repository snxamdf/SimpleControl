package sc.yhy.fileupload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 反射上传文件
 * 
 * @author YHY
 *
 */
public final class MultipartFileInjection {
	// 获得磁盘文件条目工厂
	public static DiskFileItemFactory factory;
	public static ServletFileUpload upload;

	public MultipartFileInjection() {
	}

	/**
	 * @设置 获得磁盘文件条目工厂
	 * @设置 ServletFileUpload
	 * @设置 MultipartFile类对像
	 * 
	 * @return MultipartFile
	 * @throws ServletException
	 * @throws IOException
	 */
	public MultipartFile process() throws ServletException, IOException {
		MultipartFile mf = new MultipartFile();
		// 获取临时路径
		String tempRepository = File.createTempFile("upload_", ".temp").getPath();
		tempRepository = tempRepository.substring(0, tempRepository.lastIndexOf(File.separator + "upload_"));
		if (factory == null) {
			factory = new DiskFileItemFactory();
			// 设置目录用于临时存储文件大于配置的大小阈值。
			factory.setRepository(new File(tempRepository));
			// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
			factory.setSizeThreshold(1024 * 1024);
		}
		// 文件上传处理
		if (upload == null) {
			upload = new ServletFileUpload(factory);
		}
		try {
			mf.setUpload(upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mf;
	}
}
