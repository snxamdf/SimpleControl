package sc.yhy.annotation.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * Class映射
 * 
 * @author YHY
 *
 */
@Data
public class ClassMapping implements Serializable {

	private static final long serialVersionUID = 6890475970751888120L;

	public ClassMapping() {

	}

	public ClassMapping(ClassMapping classMapping) {
		this.setClazz(classMapping.getClazz());
		this.setClassPack(classMapping.getClassPack());
		this.setMappingMethod(classMapping.getMappingMethod());
		this.setMappingRoot(classMapping.getMappingRoot());
		this.setMethodName(classMapping.getMethodName());
	}

	public ClassMapping(Class<?> clazz, String classPack, String mappingRoot, String mappingMethod, String methodName) {
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
}
