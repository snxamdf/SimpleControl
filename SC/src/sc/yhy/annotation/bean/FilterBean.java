package sc.yhy.annotation.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import lombok.Data;

/**
 * filter 配置
 * 
 * @author YHY
 *
 */
@Data
public class FilterBean implements Serializable {
	private static final long serialVersionUID = 2453442308950393237L;
	private String filterName;
	private String classPath;
	private Class<? extends Filter> clazz;
	private Map<String, String> parameter;
	private String[] mappings;

	public void setParameter(String key, String value) {
		parameter.put(key, value);
	}

	public FilterBean() {
		parameter = new HashMap<String, String>();
	}

}
