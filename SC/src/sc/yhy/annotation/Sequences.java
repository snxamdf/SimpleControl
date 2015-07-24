package sc.yhy.annotation;

import java.lang.annotation.*;

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
@Inherited
public @interface Sequences {
    //序列名称
    public String name();
}
