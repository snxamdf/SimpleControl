package sc.yhy.data;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import sc.yhy.annotation.annot.Column;
import sc.yhy.annotation.annot.Identify;
import sc.yhy.util.ReflectUtil;

public class BaseRepository<T, ID> extends AbstractBaseRepository<T, ID> {
	static final Logger logfile = Logger.getLogger(BaseRepository.class.getName());
	@SuppressWarnings("unchecked")
	private Class<T> clazz = (Class<T>) ReflectUtil.getSuperClassGenricType(getClass(), 0);

	@Override
	public int save(T entity) throws Exception {
		// 获取ID
		// identify是否为空
		boolean identifyIsEmpty = ReflectUtil.identifyIsEmpty(entity);
		if (identifyIsEmpty) {// ID不是空,执行更新操作
			super.update(entity);
		} else {// ID是空,执行插入操作
			super.insert(entity);
		}
		return 0;
	}

	@Override
	public T findOne(String id) throws SQLException {
		String tableName = ReflectUtil.getTableName(clazz);
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		String ID = "", IDVal = id;
		for (Field field : fields) {
			field.setAccessible(true);
			// 判断如果字段是Identify
			if (ReflectUtil.isAnnotation(field, Identify.class)) {
				ID = field.getName();
			}
			if (ReflectUtil.isAnnotation(field, Column.class)) {
				sql.append(field.getName()).append(",");
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" from ").append(tableName).append(" where ").append(ID).append("=?");
		List<T> ls = this.findAll(sql.toString(), new Object[] { IDVal });
		return ls != null && ls.size() > 0 ? ls.get(0) : null;
	}

	@Override
	public List<T> findAll(String sql, Object... paramValues) throws SQLException {
		List<T> ls = null;
		ConnectionBase conn = DataRepositoryThreadLocal.getConnection();// 获取数据库连接
		PreparedStatement ps = null;
		ResultSet rs = null;
		String[] columnName = null;
		try {
			ps = conn.prepareStatement(sql.toUpperCase());
			for (int i = 0; i < paramValues.length; i++) {
				ps.setObject(i + 1, paramValues[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsd = rs.getMetaData();
			int columnCount = rsd.getColumnCount();
			columnName = new String[columnCount];
			for (int i = 0; i < columnName.length; i++) {
				columnName[i] = rsd.getColumnName(i + 1).toString();
			}
			ls = new ArrayList<T>();
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
			while (rs.next()) {
				// 创建对像
				T entity = clazz.newInstance();
				for (int j = 0; j < pds.length; j++) {
					for (int i = 0; i < columnName.length; i++) {
						if (columnName[i].equalsIgnoreCase(pds[j].getName())) {
							Object value = rs.getObject(columnName[i]);
							if (value != null) {
								BeanUtils.setProperty(entity, pds[j].getName(), value);
							}
							break;
						}
					}
				}
				ls.add(entity);
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

	@Override
	public List<T> findAll() throws SQLException {
		try {
			String tableName = ReflectUtil.getTableName(clazz);
			Field[] fields = clazz.getDeclaredFields();
			System.out.println(fields.length);
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			for (Field field : fields) {
				// 判断是否为列
				if (ReflectUtil.isAnnotation(field, Column.class)) {
					sql.append(field.getName()).append(",");
				}
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" from ").append(tableName);
			List<T> ls = this.findAll(sql.toString(), new Object[] {});
			return ls;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, T> findOneBySql(String sql) throws SQLException {
		return null;
	}

	@Override
	public List<Map<String, T>> findAllBySql(String sql, Object... paramValues) throws SQLException {
		return null;
	}

}
