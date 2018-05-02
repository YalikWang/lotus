package com.lotus.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Column {
	
	public static final String DEFAULT_DB_NAME = "";
	
	public static final String DEFAULT_TYPE = "";

	/** 是否主键 */
	public boolean PK() default false;

	/** 名称 */
	public String name() default DEFAULT_DB_NAME;

	/** 类型 */
	public String type() default DEFAULT_TYPE;

	/** 长度 */
	public int length() default -1;

	/** 精度 */
	public int precision() default -1;
}
