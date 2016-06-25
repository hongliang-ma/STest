/**
 * Alibaba.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.dagger.dtest.testng.listener;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * TestNG测试类的监听器，负责在所有类被调用运行时。由于该类由TestNG创建，无法控制其为单例，
 * 而该类又主要用于做环境初始化，因此将所有属性设置成静态，保证值初始化一次
 * 
 * @author 马洪良
 * @version $Id: STestListener.java, v 0.1 2014年3月25日 下午4:23:10 马洪良 Exp $
 */
public final class DTestListener implements IMethodInterceptor,
		IInvokedMethodListener {

	/**
	 * @see org.testng.IInvokedMethodListener#afterInvocation(org.testng.IInvokedMethod,
	 *      org.testng.ITestResult)
	 */
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
	}

	/**
	 * @see org.testng.IInvokedMethodListener#beforeInvocation(org.testng.IInvokedMethod,
	 *      org.testng.ITestResult)
	 */
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	}

	/**
	 * @see org.testng.IMethodInterceptor#intercept(java.util.List,
	 *      org.testng.ITestContext)
	 */
	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods,
			ITestContext context) {
		return null;
	}

}
