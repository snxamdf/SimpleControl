package sc.yhy.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.cglib.beans.BeanMap;
import sc.yhy.annotation.annot.Column;
import sc.yhy.annotation.annot.Identify;
import sc.yhy.annotation.bean.ClassBean;
import sc.yhy.core.Entrance;
import sc.yhy.util.ReflectUtil;
import sc.yhy.util.Util;

abstract class AbstractBaseRepository<T, ID> implements Repository<T, String> {
	static final Logger logfile = Logger.getLogger(AbstractBaseRepository.class.getName());

	/**
	 * 执行的更新操作
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	int update(Object entity) throws SQLException {
		BeanMap beanMap = BeanMap.create(entity);
		Class<?> clases = beanMap.getClass();// 获取 entity class
		String className = this.getClassName(clases.getName());
		ClassBean classBean = Entrance.getClassBean(className);
		String beanName = classBean.getTableName();// 获取表名
		Field[] fields = classBean.getFields();// 获得所有字段
		// 存放子bean集合
		List<Object> beans = new ArrayList<Object>();
		// 存放子beanList集合
		List<Object> beansList = new ArrayList<Object>();
		// 存放sql语句字段
		StringBuffer sql = new StringBuffer("UPDATE ");
		sql.append(beanName);
		sql.append(" SET ");
		// 存放字段值
		List<Object> listData = new ArrayList<Object>();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (ReflectUtil.isAnnotation(field, Column.class)) {
				// 判断当前字段是否为列
				Object value = beanMap.get(fieldName);
				if (!ReflectUtil.isAnnotation(field, Identify.class) && value != null && !"".equals(value)) {
					Column column = field.getAnnotation(Column.class);
					String cm = !"".equals(column.name()) ? column.name() : fieldName;
					sql.append(cm).append("=").append("?").append(",");
					listData.add(value);
				}
			} else if (Util.isList(field.getGenericType().toString())) {
				beansList.add(beanMap.get(fieldName));
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		Object[] fieldObject = classBean.getIdentify();// 取得唯一标识
		String idFieldName = fieldObject[0].toString();
		String idFieldValue = beanMap.get(idFieldName).toString();
		sql.append(" where ").append(idFieldName).append("=").append("?");
		listData.add(idFieldValue);
		// System.out.println("SQL: " + sql.toString().toUpperCase() + "  " +
		// listData);
		int r = 1;
		r = this.update(sql.toString().toUpperCase(), listData.toArray());
		// 操作成功
		if (r > 0) {
			// 循环子bean操作
			for (Object object : beans) {
				T value = (T) object;
				if (value != null) {
					this.save(value);
				}
			}
			// 循环子beanList操作
			for (Object object : beansList) {
				this.collection(object);
			}
		}
		return r;
	}

	/**
	 * 插入操作
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	int insert(Object entity) throws SQLException {
		BeanMap beanMap = BeanMap.create(entity);
		Class<?> clases = beanMap.getClass();// 获取 entity class
		String className = this.getClassName(clases.getName());
		ClassBean classBean = Entrance.getClassBean(className);
		String beanName = classBean.getTableName();// 获取表名
		Field[] fields = classBean.getFields();// 获得所有字段
		// 存放子bean集合
		List<Object> beans = new ArrayList<Object>();
		// 存放子beanList集合
		List<Object> beansList = new ArrayList<Object>();
		// 存放sql语句字段
		StringBuffer sb_clo = new StringBuffer("INSERT INTO ");
		sb_clo.append(beanName);
		sb_clo.append("(");
		// 存放sql语句值占位符
		StringBuffer sb_val = new StringBuffer("VALUES (");
		// 存放字段值
		List<Object> listData = new ArrayList<Object>();
		// 生成唯一标识ID
		String ID = Util.uuidTwo().toUpperCase();
		try {
			for (Field field : fields) {
				String fieldName = field.getName();
				if (field.isAnnotationPresent(Column.class)) { // 判断当前字段是否为列
					// 判断是否为唯一标识
					if (field.isAnnotationPresent(Identify.class)) {
						field.setAccessible(true);
						Column column = field.getAnnotation(Column.class);
						String cm = !"".equals(column.name()) ? column.name() : fieldName;
						sb_clo.append(cm + ",");
						sb_val.append("?,");
						listData.add(ID);
						field.set(entity, ID);
					} else {
						Object value = beanMap.get(fieldName);
						if (value != null && !"".equals(value)) {
							Column column = field.getAnnotation(Column.class);
							String cm = !"".equals(column.name()) ? column.name() : fieldName;
							sb_clo.append(cm + ",");
							sb_val.append("?,");
							listData.add(value);
						}
					}
				} else if (Util.isList(field.getGenericType().toString())) {
					beansList.add(beanMap.get(fieldName));
				}

			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		sb_clo.deleteCharAt(sb_clo.length() - 1);
		sb_val.deleteCharAt(sb_val.length() - 1);
		sb_clo.append(") ");
		sb_val.append(") ");
		// System.out.println("SQL: " + sb_clo.toString().toUpperCase() +
		// sb_val.toString().toUpperCase() + listData);
		int r = 1;
		r = this.update(sb_clo.toString().toUpperCase() + sb_val.toString().toUpperCase(), listData.toArray());
		if (r > 0) {
			// 循环子bean操作
			for (Object object : beans) {
				T value = (T) object;
				if (value != null) {
					this.save(value);
				}
			}
			// 循环子beanList操作
			for (Object object : beansList) {
				this.collection(object);
			}
		}
		return r;
	}

	String getClassName(String className) {
		if (className.indexOf("$") != -1) {
			className = className.substring(0, className.indexOf("$"));
		}
		return className;
	}

	/**
	 * 处理集合对像
	 * 
	 * @param field
	 * @param bean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void collection(Object field) throws SQLException {
		List<T> list = (List<T>) field;
		for (Object objBean : list) {
			this.save((T) objBean);
		}
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
	public int update(String sql) throws SQLException {
		return this.update(sql, new Object[] {});
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
		ConnectionBase conn = DataRepositoryThreadLocal.getConnection();// 获取数据库连接
		PreparedStatement pps = null;
		try {
			pps = conn.prepareStatement(sql);
			int length = obj == null ? 0 : obj.length;
			for (int i = 0; i < length; i++) {
				pps.setObject(i + 1, obj[i]);
			}
			int r = pps.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pps != null) {
				pps.close();
				pps = null;
			}
		}
		return -1;
	}
}
