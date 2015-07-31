package com.yhy.test.entity;

import lombok.Data;
import sc.yhy.annotation.annot.BeanToTable;
import sc.yhy.annotation.annot.Column;
import sc.yhy.annotation.annot.Identify;

@Data
@BeanToTable(name = "tab_name")
public class TestBeanSon {
	@Identify
	@Column
	private String testid;
	@Column
	private String testName;
}
