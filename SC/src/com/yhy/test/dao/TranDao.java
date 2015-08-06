package com.yhy.test.dao;

import java.sql.SQLException;

import sc.yhy.annotation.annot.Dao;
import sc.yhy.data.BaseRepository;
import sc.yhy.data.DataBase;

import com.yhy.test.entity.Tran;

@Dao
public class TranDao extends BaseRepository<Tran, String> {

	public void saveTest() throws SQLException {
		Tran entity = new Tran();
		entity.setUname("张三" + Math.random());
		this.save(entity);
		int c = this.getCount();
		System.out.println(c);
		this.delete(entity);
		c = this.getCount();
		System.out.println(c);
		// System.out.println(entity);
		// entity.setUname("张三" + Math.random());
		// this.save(entity);
		DataBase.commit();
		// System.out.println(entity);
		// List<Tran> list = this.findAll();
		// System.out.println("数据个数 " + list.size());
		// this.findOne("00166263-056F-495A-9351-2CE9A4030647");
		// // System.out.println(tran);
		// String sql = "select * from tran";
		// List<Map<String, Object>> listMap = this.findBySql(sql);
		// System.out.println("数据个数 " + listMap.size());
	}

}
