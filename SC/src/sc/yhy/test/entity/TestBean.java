package sc.yhy.test.entity;

import java.util.List;

import lombok.Data;

@Data
public class TestBean {
	private String emailId;
	private String emailName;
	private String emailAddress;

	private TestBeanSon testBeanSon;

	private List<TestBeanSon> listSon;
	private List<String> listStr;
}
