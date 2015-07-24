package sc.yhy.data;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import sc.yhy.annotation.BeanToTable;

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

	/**
	 * 插入方法
	 *
	 * @param bean
	 *            实体类对像
	 * @return
	 * @throws Exception
	 */
	public int insertToClass(Object bean) throws Exception {
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
		StringBuffer sb_clo = new StringBuffer("INSERT INTO ");
		sb_clo.append(beanName);
		sb_clo.append("(");
		StringBuffer sb_val = new StringBuffer("VALUES (");
		List<Object> listData = new ArrayList<Object>();
		int res_seq = 0;
		for (PropertyDescriptor pd : pds) {
			method = pd.getReadMethod();
			if (bean.getClass().isAssignableFrom(method.getDeclaringClass())) {
				tempName = pd.getName();
				tempVal = BeanUtils.getProperty(bean, tempName);
				if (tempVal != null && !"".equals(tempVal) && !"null".equals(tempVal)) {
					sb_clo.append(tempName + ",");
					sb_val.append("?,");
					listData.add(tempVal);
				}
			}
			method = null;
		}
		beanName = tempVal = tempName = null;
		sb_clo.deleteCharAt(sb_clo.length() - 1);
		sb_val.deleteCharAt(sb_val.length() - 1);
		sb_clo.append(") ");
		sb_val.append(") ");
		System.out.println("SQL: " + sb_clo.toString().toUpperCase() + sb_val.toString().toUpperCase() + listData);
		int r = this.update(sb_clo.toString().toUpperCase() + sb_val.toString().toUpperCase(), listData.toArray());
		return r > 0 ? res_seq : r;
	}

}
