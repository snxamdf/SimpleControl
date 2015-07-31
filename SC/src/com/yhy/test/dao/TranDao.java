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
		// entity.setUid("11111");
		entity.setUname("11111");

		TestBean testBean = new TestBean();
		// testBean.setEmailId("1111");
		testBean.setEmailName("1111");
		testBean.setEmailAddress("111111址");
		entity.setTestBean(testBean);

		List<TestBean> testBeans = new ArrayList<TestBean>();
		testBean = new TestBean();
		// testBean.setEmailId("1111");
		testBean.setEmailName("1111");
		testBean.setEmailAddress("111111址");
		testBeans.add(testBean);
		testBean = new TestBean();
		// testBean.setEmailId("1111");
		testBean.setEmailName("1111");
		testBean.setEmailAddress("111111址");
		testBeans.add(testBean);
		testBean = new TestBean();
		// testBean.setEmailId("1111");
		testBean.setEmailName("1111");
		testBean.setEmailAddress("111111址");
		testBeans.add(testBean);
		entity.setTestBeans(testBeans);

		this.save(entity);
		DataBase.commit();
		end = System.currentTimeMillis();
		System.out.println("执行时间=" + (end - start));
		// List<Tran> list = this.findAll();
		// //System.out.println(list);
		// Tran tran=this.findOne("123123");
		// //System.out.println(tran);
		// DataBase.close();
	}

	public static void main(String[] args) throws Exception {
		Tran entity = new Tran();
		entity.setUid("123123");
		entity.setUname("杨红岩");

		TestBean testBean = new TestBean();
		// testBean.setEmailId("123");
		testBean.setEmailName("nameaaa");
		testBean.setEmailAddress("地址");
		entity.setTestBean(testBean);

		List<TestBean> testBeans = new ArrayList<TestBean>();
		testBeans.add(testBean);
		entity.setTestBeans(testBeans);

		TranDao dao = new TranDao();
		dao.save(entity);
	}
}
