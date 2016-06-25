package com.dagger.dtest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface PrintLog {

	// 输出的对象需要解析的层次
	// 0,表示每个参数以及返回结果自身以string的方式输出
	// n,表示每个参数以及返回结果第n层属性以string的方式输出
	int level() default 0;

	// 标识是否打印参数
	boolean param() default true;

	// 标识是否打印返回结果
	boolean result() default true;
}
