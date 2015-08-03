package sc.yhy.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import sc.yhy.util.Constant;

/**
 * 配置文件缓存加载类
 * 
 * 
 *
 */
class Properties implements Serializable {
	static final Logger logfile = Logger.getLogger(Properties.class.getName());
	private static final long serialVersionUID = 1407646372726826034L;
	private static ConcurrentHashMap<String, String> propertieMap = new ConcurrentHashMap<String, String>();

	public Properties() {
		this.init();
	}

	private void init() {
		URL url = Properties.class.getClassLoader().getResource("/");
		File file = new File(url.getPath());
		if (file.exists()) {
			File[] listFile = file.listFiles();
			for (File lf : listFile) {
				if (lf.isFile()) {
					if (lf.getName().lastIndexOf(Constant.$PROPERTIES) != -1) {
						this.loadPropertie(lf);
					}
				}
			}
		} else {
			logfile.log(Level.WARNING, "路径不存在 " + file.getPath());
		}
	}

	private void loadPropertie(File file) {
		java.util.Properties properties = new java.util.Properties();
		InputStream is = null;
		Reader r = null;
		try {
			is = new FileInputStream(file);
			r = new InputStreamReader(is, "UTF-8");
			properties.load(r);
			Set<Entry<Object, Object>> entrySet = properties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				if (!entry.getKey().toString().startsWith("#")) {
					propertieMap.put(entry.getKey().toString().trim(), entry.getValue().toString().trim());
				}
			}
		} catch (IOException e) {
			logfile.log(Level.WARNING, e.getMessage());
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException e) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e2) {
				}
			}
			properties.clear();
		}
	}

	/**
	 * 获取配置文件value
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		return propertieMap.get(key);
	}
}
