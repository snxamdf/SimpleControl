package com.yhy.test.entity;

import java.util.List;

import lombok.Data;
import sc.yhy.annotation.annot.Bean;
import sc.yhy.annotation.annot.BeanToTable;
import sc.yhy.annotation.annot.Column;

@Data
@BeanToTable(name = "users")
public class TestBean {
	@Column
	private String emailId;
	@Column
	private String emailName;
	@Column
	private String emailAddress;

	@Bean
	private TestBeanSon testBeanSon;

	private List<TestBeanSon> listSon;
	private List<String> listStr;
}
