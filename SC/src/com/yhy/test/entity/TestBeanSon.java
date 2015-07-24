package com.yhy.test.entity;

import sc.yhy.annotation.BeanToTable;
import sc.yhy.annotation.Column;
import lombok.Data;

@Data
@BeanToTable(name="tab_name")
public class TestBeanSon {
	@Column
	private String testid;
	@Column
	private String testName;
}
