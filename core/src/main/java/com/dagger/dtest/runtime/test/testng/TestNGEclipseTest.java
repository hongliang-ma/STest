/**
 * Alibaba.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.dagger.dtest.runtime.test.testng;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.testng.Assert;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import com.dagger.dtest.testng.listener.DTestListener;

/**
 * 
 * @author 马洪良
 * @version $Id: TestNGEclipseTest.java, v 0.1 2014年4月2日 下午1:46:54 马洪良 Exp $
 */
@Listeners(DTestListener.class)
public abstract class TestNGEclipseTest extends Assert implements IHookable,
		ApplicationContextAware {

	protected final Log logger = LogFactory.getLog(this.getClass());

	final protected static String CONTENTFILE = "";

	protected ApplicationContext applicationContext;

	/**
	 * 环境初始化操作
	 */
	public TestNGEclipseTest() {
		super();

	}

	/**
	 * @see org.testng.IHookable#run(org.testng.IHookCallBack,
	 *      org.testng.ITestResult)
	 */
	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
	}

	/**
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
