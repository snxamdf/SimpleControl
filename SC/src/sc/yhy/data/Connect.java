package sc.yhy.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.Map;

/**
 * @time 2015-07-29
 * @author YHY
 *
 */
public interface Connect<T> {
	public void close();

	public Connection getConnection();

	public void commit() throws SQLException;

	public void rollback(Savepoint savepoint) throws SQLException;

	public void rollback() throws SQLException;

	public int insertToClass(Object bean) throws Exception;

	public void setPageNo(int pageNo);

	public void setPageSize(int pageSize);

	public List<T> execute() throws SQLException;

	public int update(String sql) throws SQLException;

	public int update(String sql, Object... obj) throws SQLException;

	public List<Map<String, Object>> queryToListMap(String sql) throws SQLException;

	public Map<String, Object> getOneRow(String sql) throws SQLException;

	public List<Map<String, Object>> queryWithParam(String sql, Object... paramValues) throws SQLException;

	public int updateToClass(Object bean, String... where) throws Exception;

	public void createQuery(String sql, Object[] param, Class<T> clasz);

	public T queryToBean(String sql, Object[] param, Class<T> clasz) throws SQLException;

	public List<T> queryToBeans(String sql, Object[] param, Class<T> clasz) throws SQLException;

	public List<T> queryToBeans(String sql, Object[] param) throws SQLException;

	public int getTotal();
}
