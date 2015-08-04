package sc.yhy.data;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import sc.yhy.annotation.annot.Column;
import sc.yhy.annotation.annot.Identify;
import sc.yhy.annotation.bean.ClassBean;
import sc.yhy.core.Entrance;
import sc.yhy.util.ReflectUtil;

/**
 * dao 继承类，对数据操作
 * 
 * @param paramValues参数说明
 *            ，设置参数时，参数下标位置和sql语句?替换符位置要保持一致
 * 
 * @author YHY
 *
 * @param <T>
 * @param <ID>
 */
public class BaseRepository<T, ID> extends AbstractBaseRepository<T, ID> {
	static final Logger logfile = Logger.getLogger(BaseRepository.class.getName());
	private String className;
	private Class<T> clazz = null;
	private ClassBean classBean = null;
	private String beanName = null;
	private Field[] fields = null;

	/**
	 * 如果查询 初始化一些参数
	 */
	@SuppressWarnings({ "unchecked" })
	private void init() {
		if (clazz == null) {
			clazz = (Class<T>) ReflectUtil.getSuperClassGenricType(getClass(), 0);
			className = this.getClassName(clazz.getName());
			classBean = Entrance.getClassBean(className);
			beanName = classBean.getTableName();// 获取表名;
			fields = classBean.getFields();
		}
	}

	/**
	 * 保存或更新
	 */
	@Override
	public int save(T entity) throws SQLException {
		// identify是否为空
		boolean identifyIsEmpty = ReflectUtil.identifyIsEmpty(entity);
		if (identifyIsEmpty) {// ID不是空,执行更新操作
			super.update(entity);
		} else {// ID是空,执行插入操作
			super.insert(entity);
		}
		return 0;
	}

	/**
	 * ID查询一条
	 */
	@Override
	public T findOne(String id) throws SQLException {
		this.init();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		String ID = classBean.getIdentify()[0].toString(), IDVal = id;
		for (Field field : fields) {
			if (ReflectUtil.isAnnotation(field, Column.class)) {
				sql.append(field.getName()).append(",");
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" FROM ").append(beanName).append(" WHERE ").append(ID).append("=?");
		List<T> ls = this.findAll(sql.toString(), new Object[] { IDVal });
		return ls != null && ls.size() > 0 ? ls.get(0) : null;
	}

	/**
	 * 查询所有
	 */
	@Override
	public List<T> findAll() throws SQLException {
		try {
			this.init();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			for (Field field : fields) {
				// 判断是否为列
				if (ReflectUtil.isAnnotation(field, Column.class)) {
					sql.append(field.getName()).append(",");
				}
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" FROM ").append(beanName);
			List<T> ls = this.findAll(sql.toString(), new Object[] {});
			return ls;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 带查询条件查询所有
	 */
	@Override
	public List<T> findAll(String sql, Object... paramValues) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.init();
			ConnectionBase conn = DataRepositoryThreadLocal.getConnection();// 获取数据库连接
			List<T> ls = new ArrayList<T>();
			ps = conn.prepareStatement(sql.toUpperCase());
			for (int i = 0; i < paramValues.length; i++) {
				ps.setObject(i + 1, paramValues[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				// 创建对像
				T entity = clazz.newInstance();
				for (Field field : fields) {
					if (field.isAnnotationPresent(Column.class)) {
						Object value = rs.getObject(field.getName());
						if (value != null) {
							field.setAccessible(true);
							field.set(entity, value);
						}
					}
				}
				ls.add(entity);
			}
			return ls;
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
		return null;
	}

	/**
	 * 查询一条返回map
	 */
	@Override
	public Map<String, Object> findOneBySql(String sql) throws SQLException {
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {});
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 查询多个反回list map集合
	 */
	@Override
	public List<Map<String, Object>> findBySql(String sql) throws SQLException {
		return this.findBySql(sql, new Object[] {});
	}

	/**
	 * 查询多个，可设置查询条件，返回list map集合
	 */
	@Override
	public List<Map<String, Object>> findBySql(String sql, Object... paramValues) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		PreparedStatement pps = null;
		ResultSet rs = null;
		ConnectionBase conn = DataRepositoryThreadLocal.getConnection();// 获取数据库连接
		pps = conn.prepareStatement(sql);
		for (int i = 0; i < paramValues.length; i++) {
			pps.setObject(i + 1, paramValues[i]);
		}
		rs = pps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columncount = rsmd.getColumnCount();
		while (rs.next()) {
			Map<String, Object> onerow = new HashMap<String, Object>(columncount);
			for (int i = 0; i < columncount; i++) {
				String columnName = rsmd.getColumnName(i + 1);
				onerow.put(columnName, rs.getObject(i + 1));
			}
			list.add(onerow);
		}
		return list;
	}

	/**
	 * 查询数量
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getCount() throws SQLException {
		this.init();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		for (Field field : fields) {
			if (ReflectUtil.isAnnotation(field, Identify.class)) {
				sql.append("COUNT(").append(field.getName()).append(") COUNT");
				break;
			}
		}
		if (sql.indexOf("COUNT") == -1) {
			sql.append("COUNT(*) COUNT");
		}
		sql.append(" FROM ").append(beanName);
		Map<String, Object> map = this.findOneBySql(sql.toString().toUpperCase());
		int count = Integer.parseInt(map.get("COUNT").toString());
		return count;
	}
}
