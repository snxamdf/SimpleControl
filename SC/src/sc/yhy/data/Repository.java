package sc.yhy.data;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

interface Repository<T, ID extends Serializable> {

	public int save(T entity) throws SQLException;

	public List<T> findAll() throws SQLException;

	public List<T> findAll(String sql, Object... paramValues) throws SQLException;

	public T findOne(String id) throws SQLException;

	public int update(String sql) throws SQLException;

	public int update(String sql, Object... obj) throws SQLException;

	public Map<String, Object> findOneBySql(String sql) throws SQLException;

	public List<Map<String, Object>> findBySql(String sql, Object... paramValues) throws SQLException;

	public List<Map<String, Object>> findBySql(String sql) throws SQLException;

	public int getCount() throws SQLException;

}
