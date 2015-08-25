package sc.yhy.annotation.bean;

import java.io.Serializable;
import java.util.EventListener;

import lombok.Data;

@Data
public class ListenerBean implements Serializable {
	private static final long serialVersionUID = -6891954426498856088L;
	private String classPath;
	private Class<? extends EventListener> clazz;

}
