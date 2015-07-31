package sc.yhy.annotation.bean;

import java.io.Serializable;
import java.lang.reflect.Field;

import sc.yhy.util.ReflectUtil;
import lombok.Data;

@Data
public class ClassBean implements Serializable {
	private static final long serialVersionUID = -2791536966200688917L;
	private Object entity;
	private Class<?> clazz;
	private String classPack;
	private String tableName;
	private Object[] identify;
	private Field[] fields;

	public ClassBean() {
	}

	public ClassBean(Class<?> clazz, String classPack, String tableName) {
		try {
			this.entity = clazz.newInstance();
			this.identify = ReflectUtil.getIdentify(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.clazz = clazz;
		this.classPack = classPack;
		this.tableName = tableName;
		this.fields=clazz.getDeclaredFields();
	}

}
