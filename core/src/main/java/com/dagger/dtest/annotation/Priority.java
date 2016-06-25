package com.dagger.dtest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dagger.dtest.enums.PriorityLevel;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {
	PriorityLevel value() default PriorityLevel.NORMAL;
}
