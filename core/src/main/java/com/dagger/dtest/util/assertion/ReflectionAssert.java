package com.dagger.dtest.util.assertion;
///**
// * Alibaba.com Inc.
// * Copyright (c) 2004-2014 All Rights Reserved.
// */
//package com.dagger.stest.util.assertion;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.List;
//
//import junit.framework.AssertionFailedError;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import au.com.bytecode.opencsv.CSVReader;
//
///**
// * 
// * @author 马洪良
// * @version $Id: ReflectionAssert.java, v 0.1 2014年3月13日 下午2:10:41 马洪良 Exp $
// */
//public final class ReflectionAssert {
//	public static final Logger log = LoggerFactory
//			.getLogger(ReflectionAssert.class);
//
//	/**
//	 * 通过对象属性定义文件与现有对象属性进行断言
//	 * 
//	 * @param expectedPropertisFile
//	 *            对象属性文件
//	 * @param dataId
//	 *            文件中指定数据ID，位于文件中第一列，用于唯一性区分
//	 * @param actualObject
//	 *            需要进行断言的对象
//	 * @throws AssertionFailedError
//	 */
//	public static void assertObjectContainPropertis(File expectedPropertisFile,
//			String dataId, Object actualObject) throws AssertionFailedError {
//		assertNotNull("Object propertis file is null.", expectedPropertisFile);
//		if (!expectedPropertisFile.exists()) {
//			assertEquals("Object propertis file not exist. FileName="
//					+ expectedPropertisFile.getName(), true, false);
//		}
//
//		InputStream is = null;
//		try {
//			is = new FileInputStream(expectedPropertisFile);
//		} catch (Exception e) {
//			log.error("打开对象属性定义文件失败", e);
//		}
//		assertObjectContainPropertis(is, dataId, actualObject);
//	}
//
//	/**
//	 * 通过对象属性定义文件与现有对象属性进行断言
//	 * 
//	 * @param inputStream
//	 *            输入流
//	 * @param dataId
//	 *            文件中指定数据ID，位于文件中第一列，用于唯一性区分
//	 * @param actualObject
//	 *            需要进行断言的对象
//	 * @throws AssertionFailedError
//	 */
//	public static void assertObjectContainPropertis(InputStream inputStream,
//			String dataId, Object actualObject) throws AssertionFailedError {
//
//		assertNotNull("Object propertis file input stream is null.",
//				inputStream);
//
//		@SuppressWarnings("unchecked")
//		List tableList = getCsvDataList(inputStream);
//
//		assertNotNull("Object propertis list is null.", tableList);
//		int rows = tableList.size();
//		if (1 >= rows) {
//			assertEquals("Object propertis file data size is 0.", true, false);
//		}
//		String[] header = (String[]) tableList.get(0);
//		if (1 >= header.length) {
//			assertEquals("Object propertis file property size is 0.", true,
//					false);
//		}
//		boolean hasVerifyData = false;
//		for (int i = 1; i < rows; i++) {
//			String[] rowData = (String[]) tableList.get(i);
//			if (rowData[0].equals(dataId)) {
//				for (int j = 1; j < header.length; j++) {
//					hasVerifyData = true;
//					String msg = "校验数据 dataID=" + dataId + ",propertyName="
//							+ header[j];
//					if (log.isInfoEnabled()) {
//						log.info(msg);
//					}
//					String expVal = CsvParamHandler.replaceVars(rowData[j]);
//					assertPropertyEquals(msg, header[j], expVal, actualObject);
//				}
//			}
//		}
//		if (!hasVerifyData) {
//			assertEquals("没有校验过任何数据 dataID=" + dataId, true, false);
//		}
//
//	}
//
//	/**
//	 * 通过对象属性定义文件与现有对象属性进行断言
//	 * 
//	 * @param expectedPropertisFilePath
//	 *            对象属性文件路径
//	 * @param dataId
//	 *            文件中指定数据ID，位于文件中第一列，用于唯一性区分
//	 * @param actualObject
//	 *            需要进行断言的对象
//	 * @throws AssertionFailedError
//	 */
//	public static void assertObjectContainPropertis(
//			String expectedPropertisFilePath, String dataId, Object actualObject)
//			throws AssertionFailedError {
//		if (StringUtils.isBlank(expectedPropertisFilePath)) {
//			assertEquals("Object propertis file path is empty.", true, false);
//		}
//
//		InputStream is = null;
//		try {
//			is = Thread.currentThread().getContextClassLoader()
//					.getResourceAsStream(expectedPropertisFilePath);
//		} catch (Exception e) {
//			log.error("打开对象属性定义文件失败", e);
//		}
//		assertObjectContainPropertis(is, dataId, actualObject);
//	}
//
//	/**
//	 * 断言某对象属性等于一个字符串
//	 * 
//	 * @param propertyName
//	 *            属性名
//	 * @param expectedValue
//	 *            期望值
//	 * @param actualObject
//	 *            对象
//	 * @throws AssertionFailedError
//	 */
//	public static void assertPropertyEquals(String propertyName,
//			String expectedValue, Object actualObject)
//			throws AssertionFailedError {
//		assertPropertyEquals(null, propertyName, expectedValue, actualObject);
//	}
//
//	/**
//	 * 断言某对象属性等于一个字符串
//	 * 
//	 * @param message
//	 *            错误信息
//	 * @param propertyName
//	 *            属性名
//	 * @param expectedValue
//	 *            期望值 "NaN", 忽略此属性的校验/匹配任意内容 "null", 可匹配 "null", null "", 匹配 ""
//	 * @param actualObject
//	 *            对象
//	 * @throws AssertionFailedError
//	 */
//	public static void assertPropertyEquals(String message,
//			String propertyName, String expectedValue, Object actualObject)
//			throws AssertionFailedError {
//		assertNotNull("Actual object is null.", actualObject);
//		if ("NaN".equals(expectedValue)) {
//			return;
//		}
//		Object propertyValue = getProperty(actualObject, propertyName);
//		// 这里可以根据模式选择
//		// assertNotNull(propertyValue);
//		assertEquals(message, expectedValue, String.valueOf(propertyValue));
//	}
//
//	/**
//	 * 获取CSV文件内容
//	 * 
//	 * @param inputStream
//	 *            CSV文件输入流
//	 * @return CSV转换成的List<Object[]>
//	 */
//	protected static List<?> getCsvDataList(InputStream inputStream) {
//		List<?> tableList = null;
//		try {
//			InputStreamReader isr = null;
//			isr = new InputStreamReader(inputStream);
//			CSVReader csvReader = new CSVReader(isr);
//			List<?> tempList = csvReader.readAll();
//			tableList = tempList;
//		} catch (Exception e) {
//			// assertEquals("An error occur while open object propertis file.",
//			// true, false);
//		}
//		return tableList;
//	}
//
//	// /**
//	// * 使用Ongl获取对象中变量
//	// *
//	// * @param object
//	// * @param ognlExpression
//	// * @return
//	// */
//	// protected static Object getProperty(Object object, String ognlExpression)
//	// {
//	// try {
//	// OgnlContext ognlContext = new OgnlContext();
//	// ognlContext.setMemberAccess(new DefaultMemberAccess(true));
//	// Object ognlExprObj = Ognl.parseExpression(ognlExpression);
//	// return Ognl.getValue(ognlExprObj, ognlContext, object);
//	// } catch (OgnlException e) {
//	// log.error("使用Ongl获取变量失败 : " + ognlExpression, e);
//	// }
//	// return null;
//	// }
// }
