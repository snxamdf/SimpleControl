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

	public void test() throws Exception {
		Tran entity = new Tran();
		entity.setUid("123123");
		entity.setUname("杨红岩");

		TestBean testBean = new TestBean();
		testBean.setEmailId("124");
		testBean.setEmailName("bbbb");
		testBean.setEmailAddress("bbbb址");
		entity.setTestBean(testBean);

		List<TestBean> testBeans = new ArrayList<TestBean>();
		testBeans.add(testBean);
		testBeans.add(testBean);
		testBeans.add(testBean);
		entity.setTestBeans(testBeans);

		save(entity);
		//commit();
		List<Tran> list = this.findAll();
		//System.out.println(list);
		Tran tran=this.findOne("123123");
		//System.out.println(tran);
		DataBase.close();
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
