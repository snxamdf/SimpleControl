package com.yhy.test.dao;

import java.util.ArrayList;
import java.util.List;

import sc.yhy.annotation.annot.Dao;
import sc.yhy.data.BaseRepository;
import sc.yhy.data.DataBase;

import com.yhy.test.entity.TestBean;
import com.yhy.test.entity.Tran;

@Dao
public class TranDao extends BaseRepository<Tran, String> {

	public void saveTest() throws Exception {
		long start = 0l, end = 0l;
		start = System.currentTimeMillis();
		Tran entity = new Tran();
		entity.setUname("11111");

		TestBean testBean = new TestBean();
		testBean.setEmailId("1111");
		testBean.setEmailName("1111");
		testBean.setEmailAddress("111111址");
		entity.setTestBean(testBean);


		this.save(entity);
		DataBase.commit();

		List<Tran> list = this.findAll();
		System.out.println(list.size());
		Tran tran = this.findOne("11");
		System.out.println(tran);

		end = System.currentTimeMillis();
		System.out.println("执行时间=" + (end - start));

	}

	public static void main(String[] args) throws Exception {
	}
}
