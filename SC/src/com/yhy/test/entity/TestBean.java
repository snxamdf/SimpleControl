package com.yhy.test.entity;

import java.util.List;

import lombok.Data;
import sc.yhy.annotation.BeanToTable;

@Data
@BeanToTable(name = "users")
public class TestBean {
	private String emailId;
	private String emailName;
	private String emailAddress;

	private TestBeanSon testBeanSon;

	private List<TestBeanSon> listSon;
	private List<String> listStr;
}
