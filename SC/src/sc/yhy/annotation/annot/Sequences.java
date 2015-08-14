package sc.yhy.annotation.annot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * date: 14-4-17<br>
 * time: 下午2:36<br>
 * file: SequenceId.java<br>
 * @author YHY
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Sequences {
    //序列名称
    public String name();
}
