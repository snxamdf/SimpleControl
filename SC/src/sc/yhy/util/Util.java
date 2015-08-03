package sc.yhy.util;


/**
 * 工具类
 * 
 * @author YHY
 *
 */
public class Util {
	public static String uuidOne() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String uuidTwo() {
		return java.util.UUID.randomUUID().toString();
	}

	/**
	 * 检查字段类型
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isFieldType(String type) {
		if (type.indexOf(Constant.INT) != -1) {
			return true;
		} else if (type.indexOf(Constant.INTEGER) != -1) {
			return true;
		} else if (type.indexOf(Constant.DOUBLE) != -1) {
			return true;
		} else if (type.indexOf(Constant.FLOAT) != -1) {
			return true;
		} else if (type.indexOf(Constant.LONG) != -1) {
			return true;
		} else if (type.indexOf(Constant.SHORT) != -1) {
			return true;
		} else if (type.indexOf(Constant.BOOLEAN) != -1) {
			return true;
		} else if (type.indexOf(Constant.BYTE) != -1) {
			return true;
		} else if (type.indexOf(Constant.CHAR) != -1) {
			return true;
		} else if (type.indexOf(Constant.STRING) != -1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDigital(String type) {
		if (type.indexOf(Constant.INT) != -1) {
			return true;
		} else if (type.indexOf(Constant.INTEGER) != -1) {
			return true;
		} else if (type.indexOf(Constant.DOUBLE) != -1) {
			return true;
		} else if (type.indexOf(Constant.FLOAT) != -1) {
			return true;
		} else if (type.indexOf(Constant.LONG) != -1) {
			return true;
		} else if (type.indexOf(Constant.SHORT) != -1) {
			return true;
		} else if (type.indexOf(Constant.BOOLEAN) != -1) {
			return true;
		} else if (type.indexOf(Constant.BYTE) != -1) {
			return true;
		} else if (type.indexOf(Constant.CHAR) != -1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFile(String type) {
		if (type.indexOf(Constant.FILE) != -1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isList(String type) {
		String[] lists = Constant.LIST.split(",");
		for (String sl : lists) {
			if (type.indexOf(sl) != -1) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMap(String type) {
		String[] maps = Constant.MAP.split(",");
		for (String sm : maps) {
			if (type.indexOf(sm) != -1) {
				return true;
			}
		}
		return false;
	}

	public static boolean isString(String type) {
		if (type.indexOf(Constant.STRING) != -1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFieldType(String paramPack, String classType) {
		return paramPack.indexOf(classType) != -1;
	}

	public static Object conversion(String type, String value) {
		if (type.indexOf(Constant.INT) != -1 || type.indexOf(Constant.INTEGER) != -1) {
			return value != null ? Integer.parseInt(value) : value;
		} else if (type.indexOf(Constant.DOUBLE) != -1) {
			return value != null ? Double.parseDouble(value) : value;
		} else if (type.indexOf(Constant.FLOAT) != -1) {
			return value != null ? Float.parseFloat(value) : value;
		} else if (type.indexOf(Constant.LONG) != -1) {
			return value != null ? Long.parseLong(value) : value;
		} else if (type.indexOf(Constant.SHORT) != -1) {
			return value != null ? Short.parseShort(value) : value;
		} else if (type.indexOf(Constant.BOOLEAN) != -1) {
			return value != null ? Boolean.parseBoolean(value) : value;
		} else if (type.indexOf(Constant.BYTE) != -1) {
			return value != null ? Byte.parseByte(value) : value;
		} else if (type.indexOf(Constant.STRING) != -1) {
			return value;
		} else if (type.indexOf(Constant.DATE) != -1) {
			return value;
		} else {
			return null;
		}
	}

}
