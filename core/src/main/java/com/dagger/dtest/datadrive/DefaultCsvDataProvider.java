package com.dagger.dtest.datadrive;

import java.lang.reflect.Method;

/**
 * 默认的CSV DataProvider,采用与脚本文件同样的路径
 * 
 * @author 马洪良
 * @version $Id: DefaultCsvDataProvider.java, v 0.1 2014年3月13日 下午1:31:01 马洪良 Exp
 *          $
 */
public class DefaultCsvDataProvider extends CsvDataProvider {
	public DefaultCsvDataProvider(Method m) {
		super(m);
	}

	// TODO: 自由方式获取文件路径
	@Override
	public String getDataFilePath(Method m) {
		return null;
	}
}
