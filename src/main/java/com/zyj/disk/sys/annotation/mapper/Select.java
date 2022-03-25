package com.zyj.disk.sys.annotation.mapper;

import com.zyj.disk.sys.entity.BaseEntity;
import com.zyj.disk.sys.entity.MapperMatch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Select{
	/** 要查询的字段{"arg0,arg1,arg2..."} */
	String query() default "*";

	/** 参数名和 #{key} 相等即可 */
	String where() default "";

	/** 默认有多少条返回多少条 */
	String limit() default "";

	/** return type required BaseEntity */
	Class<? extends BaseEntity> result() default BaseEntity.class;

	/** 为 true 就打印 sql 语句,默认不打印 */
	boolean print() default false;

	/** 参数匹配模式: { MapperProxy.class } */
	MapperMatch mapperMatch() default MapperMatch.PARAM;
}