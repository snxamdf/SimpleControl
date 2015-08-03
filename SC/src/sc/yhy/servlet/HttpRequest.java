package sc.yhy.servlet;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import sc.yhy.util.Constant;
/**
 * @time 2015-07-29
 * @author YHY
 *
 */
public class HttpRequest {
	private Map<String, String[]> map = new ConcurrentHashMap<String, String[]>();

	public void setParamter(HttpServletRequest request) {
		map = request.getParameterMap();
	}

	public void setParamter(List<FileItem> fileItem) {
		try {
			for (FileItem item : fileItem) {
				if (item.isFormField()) {
					this.setParamter(item.getFieldName(), new String(item.getString().getBytes(Constant.ISO88591), Constant.UTF8));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void setParamter(String key, String value) {
		if (this.map.get(key) != null) {
			String[] paramMap = map.get(key);
			String[] param = new String[paramMap.length + 1];
			System.arraycopy(paramMap, 0, param, 0, paramMap.length);
			param[param.length - 1] = value;
			this.map.put(key, param);
		} else {
			this.map.put(key, new String[] { value });
		}
	}

	public String getParamter(String key) {
		return this.map.get(key) != null && this.map.get(key).length > 0 ? this.map.get(key)[0] : null;
	}

	public String[] getParamters(String key) {
		return this.map.get(key);
	}
}
