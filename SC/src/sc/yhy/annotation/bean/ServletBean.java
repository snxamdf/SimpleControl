package sc.yhy.annotation.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import lombok.Data;

/**
 * servlet 配置
 * 
 * @author YHY
 *
 */
@Data
public class ServletBean  implements Serializable{
	private static final long serialVersionUID = -32043753871796985L;
	private String servletName;
	private String classPath;
	private Class<? extends Servlet> clazz;
	private Map<String, String> parameter;
	private String[] mapping;
	private int loadOnStartup = -1;

	public void setParameter(String key, String value) {
		parameter.put(key, value);
	}

	public ServletBean() {
		parameter = new HashMap<String, String>();
	}
}
