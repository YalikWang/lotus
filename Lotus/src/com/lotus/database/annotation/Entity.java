package com.lotus.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
	/**
	 * 默认实体名称标志，表明以Bean的名称为实体名
	 */
	public static final String DEFAULT_ENTITY_NAME = "";
	
	/**
	 * 默认数据库表名标志，表明以Bean的名称为数据库表名
	 */
	public static final String DEFAULT_TABLE_NAME = "";
	
	/**
	 * 实体名称，如果默认为
	 * @return
	 */
	String name() default DEFAULT_ENTITY_NAME;
	
	/**
	 * 数据库表名
	 * @return
	 */
	String table() default DEFAULT_TABLE_NAME;
	
	/** 是否将字段自动识别为数据库字段 */
	boolean defaultColumn() default false;
}
