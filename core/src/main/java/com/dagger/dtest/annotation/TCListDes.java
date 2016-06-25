package com.dagger.dtest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 测试用例描述
 * </p>
 * 
 * @author fred.fanj
 * @version $Id: TCList.java, v 0.1 2009-3-16 下午04:57:48 fred.fanj Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TCListDes {
	/** 测试用例描述 */
	String[] casedesc();

	/** 测试用例编号 */
	String[] caseid();
}