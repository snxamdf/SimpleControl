package sc.yhy.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * jsp 判断标签
 * 
 * @author YHY
 *
 */
public class ScIf extends SimpleTagSupport {

	private boolean test;

	public void setTest(boolean test) {
		this.test = test;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (test) {
			this.getJspBody().invoke(null);
		}
	}
}
