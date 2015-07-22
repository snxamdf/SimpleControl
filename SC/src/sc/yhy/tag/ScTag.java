package sc.yhy.tag;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ScTag extends SimpleTagSupport {
	@SuppressWarnings("unused")
	private Object items;
	private String var;
	private Collection<Object> collection;

	@SuppressWarnings("unchecked")
	public void setItems(Object items) {
		this.items = items;
		if (items instanceof Collection) {
			collection = (Collection<Object>) items;
		}
		if (items instanceof Map) {
			@SuppressWarnings("rawtypes")
			Map map = (Map) items;
			collection = map.entrySet();
		}
		// 这个是最重要的，反射判断
		if (items.getClass().isArray()) {
			this.collection = new ArrayList<Object>();
			int length = Array.getLength(items);
			for (int i = 0; i < length; i++) {
				Object value = Array.get(items, i);
				collection.add(value);
			}
		}

	}

	public void doTag() throws JspException, IOException {
		Iterator<?> it = this.collection.iterator();
		while (it.hasNext()) {
			Object value = it.next();
			this.getJspContext().setAttribute(var, value);
			this.getJspBody().invoke(null);
		}
	}

	public void setVar(String var) {
		this.var = var;
	}

}
