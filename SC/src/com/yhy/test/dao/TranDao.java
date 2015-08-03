package com.yhy.test.dao;

import java.util.List;

import sc.yhy.annotation.annot.Dao;
import sc.yhy.data.BaseRepository;
import sc.yhy.data.DataBase;

import com.yhy.test.entity.Tran;

@Dao
public class TranDao extends BaseRepository<Tran, String> {

	public void saveTest() throws Exception {
//		Tran entity = new Tran();
//		entity.setUname("张三" + Math.random());
//		this.save(entity);
//		System.out.println(entity);
//		entity.setUname("张三" + Math.random());
//		this.save(entity);
//		DataBase.commit();
//		System.out.println(entity);

		List<Tran> list = this.findAll();
		System.out.println(list.size());
		Tran tran = this.findOne("00166263-056F-495A-9351-2CE9A4030647");
		System.out.println(tran);
		DataBase.close();
	}

	public static void main(String[] args) throws Exception {
	}
}
