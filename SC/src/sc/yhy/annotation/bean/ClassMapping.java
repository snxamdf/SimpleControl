package sc.yhy.annotation.bean;

import java.io.Serializable;

public class ClassMapping implements Serializable {

	private static final long serialVersionUID = 6890475970751888120L;

	public ClassMapping(Class<?> clazz, String classPack, String mappingRoot, String mappingMethod, String methodName) {
		super();
		this.clazz = clazz;
		this.classPack = classPack;
		this.mappingRoot = mappingRoot;
		this.mappingMethod = mappingMethod;
		this.methodName = methodName;
	}

	private Class<?> clazz;
	private String classPack;
	private String mappingRoot;
	private String mappingMethod;
	private String methodName;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getClassPack() {
		return classPack;
	}

	public void setClassPack(String classPack) {
		this.classPack = classPack;
	}

	public String getMappingRoot() {
		return mappingRoot;
	}

	public void setMappingRoot(String mappingRoot) {
		this.mappingRoot = mappingRoot;
	}

	public String getMappingMethod() {
		return mappingMethod;
	}

	public void setMappingMethod(String mappingMethod) {
		this.mappingMethod = mappingMethod;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
