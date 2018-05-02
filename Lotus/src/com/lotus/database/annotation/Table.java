package com.lotus.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	/** 数据表名称 */
	String name() default "";

	/** 是否将字段自动识别为数据库字段 */
	boolean defaultColumn() default false;
}
