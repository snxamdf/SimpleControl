package com.yhy.test.entity;

import java.util.List;

import lombok.Data;
import sc.yhy.annotation.annot.Bean;
import sc.yhy.annotation.annot.BeanToTable;
import sc.yhy.annotation.annot.Column;
import sc.yhy.annotation.annot.Identify;
import sc.yhy.annotation.annot.OneToMany;
import sc.yhy.annotation.annot.OneToOne;

@Data
@BeanToTable(name = "tran")
public class Tran {
	@Identify
	@Column
	private String uid;
	@Column
	private String uname;

	@Bean
	@Column
	@OneToOne(name = "emailId")
	private TestBean testBean;

	@OneToMany(name = "uid")
	private List<TestBeanSon> testBeanSons;

}
