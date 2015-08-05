package sc.yhy.annotation.bean;

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
public class FilterBean {
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
