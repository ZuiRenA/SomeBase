package project.shen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by shen on 2019/9/2
 * at 23:57
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Required {
}
