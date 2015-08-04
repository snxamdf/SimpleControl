package sc.yhy.data;

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
	/**
	 * 随机获取一个连接池别名
	 * 
	 * @return
	 */
	public static String getDataSourceAlias() {
		// 获得连接池别名
		String[] aliass = ProxoolFacade.getAliases();
		int size = aliass.length;
		String alias = null;
		// 随机连接
		int round = (int) (Math.random() * size);
		if (aliass != null) {
			round = 1;
			alias = aliass[round];
		}
		return alias;
	}

	/**
	 * 获取连接池详细信息
	 * 
	 * @param alias
	 * @return
	 */
	public static String getConnectionPool(String alias) {
		try {
			// 连接信息
			ConnectionPoolDefinitionIF def = ProxoolFacade.getConnectionPoolDefinition(alias);
			return def.toString();
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
		return null;
	}
}
