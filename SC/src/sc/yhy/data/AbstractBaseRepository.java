package sc.yhy.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import sc.yhy.annotation.annot.Bean;
import sc.yhy.annotation.annot.Column;
import sc.yhy.annotation.annot.Identify;
import sc.yhy.util.ReflectUtil;
import sc.yhy.util.Util;

abstract class AbstractBaseRepository<T, ID> implements Repository<T, String> {
	static final Logger logfile = Logger.getLogger(AbstractBaseRepository.class.getName());

	/**
	 * 更新操作
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	int update(Object entity) throws Exception {
		Class<?> clases = ReflectUtil.getClass(entity);// 获取 entity class
		String beanName = ReflectUtil.getTableName(clases);// 获取表名
		Field[] fields = clases.getDeclaredFields();
		// 存放子bean集合
		List<Field> beans = new ArrayList<Field>();
		// 存放子beanList集合
		List<Field> beansList = new ArrayList<Field>();
		// 存放sql语句字段
		StringBuffer sql = new StringBuffer("UPDATE ");
		sql.append(beanName);
		sql.append(" SET ");
		// 存放字段值
		List<Object> listData = new ArrayList<Object>();
		for (Field field : fields) {
			if (clases.isAssignableFrom(field.getDeclaringClass())) {
				String fieldName = field.getName();
				if (ReflectUtil.isAnnotation(field, Bean.class)) {
					// 判断当前字段是否为子bean
					// 添加到list待主表操作完成后再操作子bean
					beans.add(field);
				} else if (ReflectUtil.isAnnotation(field, Column.class)) {
					// 判断当前字段是否为列
					String value = BeanUtils.getProperty(entity, fieldName);
					if (!ReflectUtil.isAnnotation(field, Identify.class) && StringUtils.isNotEmpty(value)) {
						Column column = field.getAnnotation(Column.class);
						String cm = !"".equals(column.name()) ? column.name() : fieldName;
						sql.append(cm).append("=").append("?").append(",");
						listData.add(value);
					}
				} else if (Util.isList(field.getGenericType().toString())) {
					beansList.add(field);
				}
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		Object[] fieldObject = ReflectUtil.getIdentify(entity);
		sql.append(" where ").append(fieldObject[0]).append("=").append("?");
		listData.add(fieldObject[1]);
		// System.out.println("SQL: " + sql.toString().toUpperCase() + "  " +
		// listData);
		int r = 1;
		r = this.update(sql.toString().toUpperCase(), listData.toArray());
		// 操作成功
		if (r > 0) {
			// 循环子bean操作
			for (Field field : beans) {
				String fieldName = field.getName();
				T value = (T) clases.getMethod(ReflectUtil.toGetMethod(fieldName)).invoke(entity);
				if (value != null) {
					this.save(value);
				}
			}
			// 循环子beanList操作
			for (Field field : beansList) {
				this.collection(field, entity);
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
	int insert(Object entity) throws Exception {
		Class<?> clases = ReflectUtil.getClass(entity);// 获取 entity class
		String beanName = ReflectUtil.getTableName(clases);// 获取表名
		Field[] fields = clases.getDeclaredFields();
		// 存放子bean集合
		List<Field> beans = new ArrayList<Field>();
		// 存放子beanList集合
		List<Field> beansList = new ArrayList<Field>();
		// 存放sql语句字段
		StringBuffer sb_clo = new StringBuffer("INSERT INTO ");
		sb_clo.append(beanName);
		sb_clo.append("(");
		// 存放sql语句值占位符
		StringBuffer sb_val = new StringBuffer("VALUES (");
		// 存放字段值
		List<Object> listData = new ArrayList<Object>();
		for (Field field : fields) {
			if (clases.isAssignableFrom(field.getDeclaringClass())) {
				String fieldName = field.getName();
				if (ReflectUtil.isAnnotation(field, Bean.class)) {
					// 判断当前字段是否为子bean
					// 添加到list待主表操作完成后再操作子bean
					beans.add(field);
				} else if (ReflectUtil.isAnnotation(field, Column.class)) {
					// 判断当前字段是否为列
					String value = BeanUtils.getProperty(entity, fieldName);
					if (StringUtils.isNotEmpty(value)) {
						Column column = field.getAnnotation(Column.class);
						String cm = !"".equals(column.name()) ? column.name() : fieldName;
						sb_clo.append(cm + ",");
						sb_val.append("?,");
						listData.add(value);
					}
				} else if (Util.isList(field.getGenericType().toString())) {
					beansList.add(field);
				}
			}
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
			for (Field field : beans) {
				String fieldName = field.getName();
				T value = (T) clases.getMethod(ReflectUtil.toGetMethod(fieldName)).invoke(entity);
				if (value != null) {
					this.save(value);
				}
			}
			// 循环子beanList操作
			for (Field field : beansList) {
				this.collection(field, entity);
			}
		}
		return r;
	}

	/**
	 * 集合对像
	 * 
	 * @param field
	 * @param bean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	void collection(Field field, Object bean) throws Exception {
		Type tp = field.getGenericType();
		if (tp == null)
			return;
		if (tp instanceof ParameterizedType) {// 判断是否为泛型
			ParameterizedType pt = (ParameterizedType) tp;
			Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
			if (!Util.isFieldType(genericClazz.getName())) {
				String getMeghtod = ReflectUtil.toGetMethod(field.getName());
				Object value = bean.getClass().getMethod(getMeghtod).invoke(bean);
				if (value != null) {
					List<Object> listObj = (List<Object>) value;
					for (Object objBean : listObj) {
						this.save((T) objBean);
					}
				}
			}
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
			return pps.executeUpdate();
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

	public void close() {
		DataBase.close();
	}

	public void commit() throws SQLException {
		DataBase.commit();
	}

	public void rollback() throws SQLException {
		DataBase.rollback();
	}
}
