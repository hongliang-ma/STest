/**
 * Alibaba.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.dagger.dtest.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * 
 * @author 马洪良
 * @version $Id: STestNgTestBase.java, v 0.1 2014年3月13日 下午7:39:50 马洪良 Exp $
 */
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public class DTestTestBase extends AbstractTestNGSpringContextTests {

	@Test
	public void orderData() {
		// PushDataRequest request = new PushDataRequest();
		// request.setHavanaID(25770350507L);
		// request.setCodeType("order");
		// request.setMteeCode("aliyun_data_push");
		//
		// Map<String, String> parameters = new HashMap<String, String>();
		// // 订购数量
		// parameters.put("num", "1");
		//
		// for (int i = 0; i < 15; i++) {
		// Long createTime = (new Date()).getTime() / 1000;
		// parameters.put("time", createTime.toString());
		//
		// request.setParameters(parameters);
		// System.out.println("pushData!");
		// riskControlApi.pushData(request);
		// }

		System.out.println("hello owrd!");
	}

}
