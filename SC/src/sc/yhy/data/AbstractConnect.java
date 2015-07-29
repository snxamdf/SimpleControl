package sc.yhy.data;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import sc.yhy.annotation.BeanToTable;
import sc.yhy.util.Util;

/**
 * @time 2015-07-29
 * @author YHY
 *
 */
abstract class AbstractConnect<T> implements Connect<T> {
	static final Logger logfile = Logger.getLogger(AbstractConnect.class.getName());
	Connection conn = null;
	Statement st = null;
	PreparedStatement pps = null;
	ResultSet rs = null;
	boolean isAutoCommit = false;
	int pageNo = -1;
	int pageSize = -1;
	int total = -1;

	// 加载驱动
	static {
		try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建数据库连接
	 *
	 * @return
	 */
	public Connection getConnection() {
		return this.conn;
	}

	/**
	 * 创建数据库连接
	 *
	 * @return
	 */
	void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 提交
	 *
	 * @throws java.sql.SQLException
	 */
	public void commit() throws SQLException {
		if (conn != null) {
			conn.commit();
		}
	}

	/**
	 * 回滚
	 *
	 * @throws java.sql.SQLException
	 */
	public void rollback() throws SQLException {
		if (conn != null) {
			conn.rollback();
		}
	}

	/**
	 * 回滚
	 *
	 * @throws java.sql.SQLException
	 */
	public void rollback(Savepoint savepoint) throws SQLException {
		if (conn != null) {
			conn.rollback(savepoint);
		}
	}

	/**
	 * 关闭数据库各种资源Connection Statement PreparedStatement ResultSet的方法
	 */
	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pps != null) {
			try {
				pps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
					logfile.info("数据库对像" + conn + "关闭成功");
				} catch (SQLException e) {
					e.printStackTrace();
					logfile.info("数据库对像" + conn + "关闭失败");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为Sql语句的占位符赋值
	 *
	 * @param param
	 *            参数列表
	 * @param pStatement
	 *            pStatement对象
	 * @throws java.sql.SQLException
	 */
	protected void addParamters(Object[] param, PreparedStatement pStatement) throws SQLException {
		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				pStatement.setObject(i + 1, param[i]);
			}
		}
	}

	/**
	 * 集合对像
	 * 
	 * @param field
	 * @param bean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected void collection(Field field, Object bean) throws Exception {
		Type tp = field.getGenericType();
		if (tp == null)
			return;
		if (tp instanceof ParameterizedType) {// 判断是否为泛型
			ParameterizedType pt = (ParameterizedType) tp;
			Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
			if (!Util.isFieldType(genericClazz.getName())) {
				String getMeghtod = toGetMethod(field.getName());
				Object value = bean.getClass().getMethod(getMeghtod).invoke(bean);
				if (value != null) {
					List<Object> listObj = (List<Object>) value;
					for (Object objBean : listObj) {
						this.insertToClass(objBean);
					}
				}
			}
		}
	}

	/**
	 * @param sql
	 *            String 类型的SQL语句
	 * @return Integer 受影响的行数
	 */
	public int update(String sql) {
		int row = -1;
		try {
			if (conn == null || conn.isClosed()) {
				conn = getConnection();
			}
			st = conn.createStatement();
			row = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * 更新数据库表方法
	 *
	 * @param sql
	 *            String 类型的SQL语句（insert delete update）
	 * @param obj
	 *            存放动态参数的数组
	 * @return Integer 受影响的行数
	 */
	public int update(String sql, Object... obj) throws SQLException {
		try {
			if (conn == null || conn.isClosed()) {
				conn = getConnection();
			}
			pps = conn.prepareStatement(sql);
			int length = 0;
			ParameterMetaData pmd = pps.getParameterMetaData();
			length = pmd.getParameterCount();
			for (int i = 0; i < length; i++) {
				pps.setObject(i + 1, obj[i]);
			}
			return pps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * @param sql
	 *            查询语句
	 * @return　Map<String,Object> 返回map集合一条数据
	 */
	public Map<String, Object> getOneRow(String sql) throws SQLException {
		List<Map<String, Object>> list = queryToListMap(sql);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * @param sql
	 *            查询语句
	 * @return List<Map<String,Object>> 返回list<map>集合
	 */
	public List<Map<String, Object>> queryToListMap(String sql) throws SQLException {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		if (conn == null || conn.isClosed()) {
			conn = getConnection();
		}
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columncount = rsmd.getColumnCount();
		while (rs.next()) {
			HashMap<String, Object> onerow = new HashMap<String, Object>();
			for (int i = 0; i < columncount; i++) {
				String columnName = rsmd.getColumnName(i + 1);
				onerow.put(columnName, rs.getObject(i + 1));
			}
			list.add(onerow);
		}
		return list;
	}

	/**
	 * 查询
	 *
	 * @param sql
	 *            查询语句
	 * @param paramValues
	 *            查询条件参数值
	 * @return List<Map<String,Object>> 返回list<map>集合
	 */
	public List<Map<String, Object>> queryWithParam(String sql, Object... paramValues) throws SQLException {
		// 创建集合列表用以保存所有查询到的记录
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		if (conn == null || conn.isClosed()) {
			conn = getConnection();
		}
		pps = conn.prepareStatement(sql);
		for (int i = 0; i < paramValues.length; i++) {
			pps.setObject(i + 1, paramValues[i]);
		}
		rs = pps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columncount = rsmd.getColumnCount();
		while (rs.next()) {
			HashMap<String, Object> onerow = new HashMap<String, Object>();
			for (int i = 0; i < columncount; i++) {
				String columnName = rsmd.getColumnName(i + 1);
				onerow.put(columnName, rs.getObject(i + 1));
			}
			list.add(onerow);
		}
		return list;
	}

	/**
	 * 更新方法
	 *
	 * @param bean
	 *            实体类对像
	 * @param where
	 *            更新条件 实体类属性名
	 * @return
	 * @throws Exception
	 */
	public int updateToClass(Object bean, String... where) throws Exception {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean.getClass());
		Method method = null;
		String tempVal = null, tempName = null, beanName;
		Class<?> clases = bean.getClass();
		if (clases.isAnnotationPresent(BeanToTable.class)) {
			BeanToTable beanTable = (BeanToTable) clases.getAnnotation(BeanToTable.class);
			beanName = beanTable.name();
		} else {
			beanName = clases.getName();
			beanName = beanName.substring(beanName.lastIndexOf(".") + 1, beanName.length());
		}
		clases = null;
		StringBuffer sb_clo = new StringBuffer("UPDATE ");
		sb_clo.append(beanName);
		sb_clo.append(" SET ");
		List<Object> listData = new ArrayList<Object>();
		for (PropertyDescriptor pd : pds) {
			method = pd.getReadMethod();
			if (bean.getClass().isAssignableFrom(method.getDeclaringClass())) {
				tempName = pd.getName();
				tempVal = BeanUtils.getProperty(bean, tempName);
				if (where != null && where.length != 0) {
					for (String wr : where) {
						if (!tempName.equalsIgnoreCase(wr) && tempVal != null && !"".equals(tempVal) && !"null".equals(tempVal)) {
							sb_clo.append(tempName + "=?,");
							listData.add(tempVal);
						}
					}
				} else {
					if (tempVal != null && !"".equals(tempVal) && !"null".equals(tempVal)) {
						sb_clo.append(tempName + "=?,");
						listData.add(tempVal);
					}
				}
			}
			method = null;
		}
		sb_clo.deleteCharAt(sb_clo.length() - 1);
		if (where != null && where.length != 0) {
			sb_clo.append(" WHERE ");
			for (String wr : where) {
				tempVal = BeanUtils.getProperty(bean, wr);
				sb_clo.append(wr + "=? ");
				listData.add(tempVal);
			}
		}
		beanName = tempVal = tempName = null;
		System.out.println("SQL:" + sb_clo.toString().toUpperCase() + listData);
		return this.update(sb_clo.toString().toUpperCase(), listData.toArray());
	}

	/**
	 * 查询
	 *
	 * @param sql
	 *            语句
	 * @param param
	 *            参数
	 * @param clasz
	 *            bean.class
	 * @return 返回集合数据 存放在List<clasz>泛型bean对像中
	 * @throws java.sql.SQLException
	 */
	public List<T> queryToBeans(String sql, Object[] param, Class<T> clasz) throws SQLException {
		this.createQuery(sql, param, clasz);
		return this.execute();
	}

	private String sql;
	private Object[] param;
	private Class<T> clasz;

	public void createQuery(String sql, Object[] param, Class<T> clasz) {
		this.sql = sql;
		this.param = param;
		this.clasz = clasz;
	}

	// 从第几页开始取
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	// 取出几条记录
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	// 获取总数据数
	public int getTotal() {
		if (this.total != -1) {
			double total = this.total;
			double pageSize = this.pageSize;
			total = Math.ceil(total / pageSize);
			this.total = (int) total;
		}
		return total;
	}

	public List<T> execute() throws SQLException {
		List<T> ls = null;
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String[] columnName = null;
		try {
			String sql;
			if (this.pageNo != -1 && this.pageSize != -1) {
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT * FROM (SELECT T.*, ROWNUM RN FROM (");
				sb.append(this.sql.toUpperCase());
				sb.append(") T WHERE ROWNUM <= " + (pageNo * pageSize) + ") TA WHERE TA.RN > " + ((pageNo - 1) * pageSize) + "");
				sql = sb.toString();
			} else {
				sql = this.sql;
			}
			// System.out.println("SQL " + sql);
			ps = conn.prepareStatement(sql);
			addParamters(param, ps);
			rs = ps.executeQuery();
			ResultSetMetaData rsd = rs.getMetaData();
			int columnCount = rsd.getColumnCount();
			columnName = new String[columnCount];
			for (int i = 0; i < columnName.length; i++) {
				columnName[i] = rsd.getColumnName(i + 1).toString();
			}
			ls = new ArrayList<T>();
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clasz);
			while (rs.next()) {
				T obj = clasz.newInstance();
				for (int j = 0; j < pds.length; j++) {
					for (int i = 0; i < columnName.length; i++) {
						if (columnName[i].equalsIgnoreCase(pds[j].getName())) {
							Object value = rs.getObject(columnName[i]);
							if (value != null) {
								BeanUtils.setProperty(obj, pds[j].getName(), value);
							}
							break;
						}
					}
				}
				ls.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
		}
		try {
			if (this.pageNo != -1 && this.pageSize != -1) {
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT COUNT(" + columnName[0] + ") FROM (");
				sb.append(this.sql.toUpperCase());
				sb.append(")");
				sql = sb.toString();
				System.out.println("SQL: " + sql);
				ps = conn.prepareStatement(sql);
				addParamters(param, ps);
				rs = ps.executeQuery();
				while (rs.next()) {
					this.total = rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
		}
		return ls;
	}

	/**
	 * 查询
	 *
	 * @param sql
	 *            语句
	 * @param param
	 *            参数
	 * @param clasz
	 *            bean.class
	 * @return 返回一条数据 存放在clasz泛型bean对像中
	 * @throws java.sql.SQLException
	 */
	public T queryToBean(String sql, Object[] param, Class<T> clasz) throws SQLException {
		List<T> ls = this.queryToBeans(sql, param, clasz);
		return (ls != null && ls.size() > 0 ? ls.get(0) : null);
	}

	String toGetMethod(String fieldName) {
		return "get" + toFirstLetterUpperCase(fieldName);
	}

	String toSetMethod(String fieldName) {
		return "set" + toFirstLetterUpperCase(fieldName);
	}

	private String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		String firstLetter = str.substring(0, 1).toUpperCase();
		return firstLetter + str.substring(1, str.length());
	}
}
