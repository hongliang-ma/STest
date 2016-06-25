package com.dagger.dtest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试数据文件描述
 * 
 * @author 马洪良
 * @version $Id: TestData.java, v 0.1 2014年3月13日 下午1:43:52 马洪良 Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestDataDes {
	/** 测试数据驱动文件名称 */
	String fileName();

	/** 测试数据驱动文件目录 */
	String path();
}
