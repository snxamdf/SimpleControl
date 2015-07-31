package com.yhy.test.dao;

import sc.yhy.annotation.annot.Dao;
import sc.yhy.data.BaseRepository;
import sc.yhy.data.DataBase;

import com.yhy.test.entity.Tran;

@Dao
public class TranDao extends BaseRepository<Tran, String> {

	public void saveTest() throws Exception {
		long start = 0l, end = 0l;
		start = System.currentTimeMillis();
		Tran entity = new Tran();
		entity.setUname("张三" + Math.random());
		this.save(entity);
		DataBase.commit();
		System.out.println(entity);
		entity.setUname("张三" + Math.random());
		this.save(entity);
		DataBase.commit();
		System.out.println(entity);

		// List<Tran> list = this.findAll();
		// System.out.println(list.size());
		// Tran tran = this.findOne("11");
		// System.out.println(tran);

		end = System.currentTimeMillis();
		System.out.println("执行时间=" + (end - start));

	}

	public static void main(String[] args) throws Exception {
	}
}
