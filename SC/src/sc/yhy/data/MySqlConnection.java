package sc.yhy.data;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import sc.yhy.annotation.Bean;
import sc.yhy.annotation.BeanToTable;
import sc.yhy.annotation.Column;
import sc.yhy.util.Util;

/**
 * @time 2015-07-29
 * @author YHY
 *
 */
class MySqlConnection<T> extends AbstractConnect<T> {

	public MySqlConnection() throws SQLException {
		initConn();
	}

	/**
	 * ************** 初始化连接
	 *
	 * @throws java.sql.SQLException
	 */
	private void initConn() throws SQLException {
		try {
			if (conn == null) {
				conn = DriverManager.getConnection("proxool.mysqldb");
			}
			conn.setAutoCommit(isAutoCommit);
		} catch (SQLException e) {
			logfile.info("不能创建数据库连接！");
			throw e;
		}
	}

	public int insertToClass(Object bean) throws Exception {
		String tempVal = null, tempName = null, column = null, beanName = null;
		Class<?> clases;
		if (!(bean instanceof Class)) {
			clases = bean.getClass();
		} else {
			clases = (Class<?>) bean;
		}
		if (clases.isAnnotationPresent(BeanToTable.class)) {
			BeanToTable beanTable = (BeanToTable) clases.getAnnotation(BeanToTable.class);
			beanName = beanTable.name();
		} else {
			beanName = clases.getName();
			beanName = beanName.substring(beanName.lastIndexOf(".") + 1, beanName.length());
		}
		StringBuffer sb_clo = new StringBuffer("INSERT INTO ");
		sb_clo.append(beanName);
		sb_clo.append("(");
		StringBuffer sb_val = new StringBuffer("VALUES (");
		List<Object> listData = new ArrayList<Object>();
		Field[] fields = clases.getDeclaredFields();
		for (Field fied : fields) {
			if (clases.isAssignableFrom(fied.getDeclaringClass())) {
				tempName = fied.getName();
				if (fied.isAnnotationPresent(Bean.class)) {
					Object value = clases.getMethod(super.toGetMethod(tempName)).invoke(bean);
					if (value != null) {
						this.insertToClass(value);
					}
				} else if (fied.isAnnotationPresent(Column.class)) {
					tempVal = BeanUtils.getProperty(bean, tempName);
					if (tempVal != null && !"".equals(tempVal) && !"null".equals(tempVal)) {
						Column cm = fied.getAnnotation(Column.class);
						column = !"".equals(cm.name()) ? cm.name() : tempName;
						sb_clo.append(column + ",");
						sb_val.append("?,");
						listData.add(tempVal);
					}
				} else if (Util.isList(fied.getGenericType().toString())) {
					this.collection(fied, bean);
				} else if (Util.isMap(fied.getGenericType().toString())) {
					System.out.println(fied);
				}
			}
		}
		beanName = tempVal = tempName = null;
		sb_clo.deleteCharAt(sb_clo.length() - 1);
		sb_val.deleteCharAt(sb_val.length() - 1);
		sb_clo.append(") ");
		sb_val.append(") ");
		System.out.println("SQL: " + sb_clo.toString().toUpperCase() + sb_val.toString().toUpperCase() + listData);
		int r = this.update(sb_clo.toString().toUpperCase() + sb_val.toString().toUpperCase(), listData.toArray());
		return r;
	}

}
