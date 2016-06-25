/**
 * Alibaba.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.dagger.dtest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试bean和接口调用的上下文
 * 
 * 
 * @author 马洪良
 * @version $Id: AtsSpringContext.java, v 0.1 2014年3月13日 下午1:34:08 马洪良 Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface CaseSpringContext {
	boolean inheritLocations() default true;

	String[] locations() default {};

	// 需要过滤velocity标签的文件名,{"*"}表示所有的文件都需要过滤
	String[] resourceToFilter() default {};

	String[] webServiceLocations() default {};
}
