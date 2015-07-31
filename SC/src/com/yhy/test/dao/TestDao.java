package com.yhy.test.dao;

import java.sql.SQLException;

import sc.yhy.annotation.annot.Dao;
import sc.yhy.data.BaseRepository;

import com.yhy.test.entity.TestBean;

@Dao
public class TestDao extends BaseRepository<TestBean, String> {
	public void print() throws SQLException {
	}
}
