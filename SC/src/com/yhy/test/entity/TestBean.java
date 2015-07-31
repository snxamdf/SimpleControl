package com.yhy.test.entity;

import lombok.Data;
import sc.yhy.annotation.annot.BeanToTable;
import sc.yhy.annotation.annot.Column;
import sc.yhy.annotation.annot.Identify;

@Data
@BeanToTable(name = "users")
public class TestBean {
	@Identify
	@Column
	private String emailId;
	@Column
	private String emailName;
	@Column
	private String emailAddress;

//	@Bean
//	private TestBeanSon testBeanSon;
//
//	private List<TestBeanSon> listSon;
//	private List<String> listStr;
}
