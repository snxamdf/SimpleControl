package sc.yhy.annotation.util;

import sc.yhy.annotation.Constant;

public class Util {
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

	public static boolean isList(String type) {
		if (type.indexOf(Constant.LIST) != -1) {
			return true;
		} else {
			return false;
		}
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
