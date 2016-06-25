package com.dagger.dtest.datadrive;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * 数据提供接口
 * 
 * @author 马洪良
 * @version $Id: DefaultCsvDataProvider.java, v 0.1 2014年3月13日 下午1:30:23 马洪良 Exp
 *          $
 */
public interface IDataProvider extends Iterator<Object[]> {
	// 可以通过该函数设置不同的文件路径规则
	public String getDataFilePath(Method m);
}
