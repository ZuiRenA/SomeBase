package com.shen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * created by shen at 2019/9/2 11:05
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Required {
}
