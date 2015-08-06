package sc.yhy.data;

import java.sql.SQLException;

import org.logicalcobwebs.proxool.ConnectionPoolDefinitionIF;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;

/**
 * 连接池信息
 * 
 * @author YHY
 *
 */
public class DataSourceType {
	public DataSourceType() {

	}

	/**
	 * 随机获取一个连接池别名
	 * 
	 * @return
	 */
	static String getDataSourceAlias() {
		String alias = null;
		// 获得连接池别名
		String[] aliass = ProxoolFacade.getAliases();
		int size = aliass.length;
		// 随机连接
		int round = (int) (Math.random() * size);
		if (aliass != null) {
			alias = aliass[round];
		}
		return alias;
	}

	/**
	 * 在关闭连接之前获取,获取当前连接池详细信息
	 * 
	 * @param alias
	 * @return
	 */
	public static ConnectionPoolDefinitionIF getConnectionPool() {
		try {
			String alias = DataRepositoryThreadLocal.getConnection().getAlias();
			// 连接信息
			ConnectionPoolDefinitionIF def = ProxoolFacade.getConnectionPoolDefinition(alias);
			return def;
		} catch (ProxoolException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
