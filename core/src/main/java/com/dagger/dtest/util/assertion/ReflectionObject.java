/**
 * Alibaba.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.dagger.dtest.util.assertion;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 马洪良
 * @version $Id: ReflectionObject.java, v 0.1 2014年3月13日 下午2:12:24 马洪良 Exp $
 */
public final class ReflectionObject {
	/**
	 * 默认不继承且所有属性当简单属性输出
	 */
	public static final int DEFAULT = 0;

	/**
	 * 继承属性
	 */
	public static final int INHERITE = 1 << 1;

	/**
	 * 将所有属性当做简单类型处理
	 */
	public static final int SIMPLE = 1 << 2;

	/**
	 * 只需要简单类型，过滤复杂对象类型
	 */
	public static final int ONLY_SIMPLE = 1 << 3;

	// 记录所分析的类型
	private static Class<?> CLASS_TYPE = null;

	/**
	 * 判断变量是否重名
	 * 
	 * @param field
	 * @return true: 重名
	 */
	protected static boolean checkDuplicateFieldName(List<Field> objFields,
			Field field) {
		for (Field objField : objFields) {
			if (objField.getName().equals(field.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查选项
	 * 
	 * @param check
	 *            检查点
	 * @param option
	 * @return
	 */
	protected static boolean checkOption(int check, int option) {
		return ((option & check) == check);
	}

	/**
	 * @param clz
	 * @param fieldsList
	 * @param option
	 * @param fieldContainer
	 *            记录当前field的上层类型
	 */
	protected static void getField(Field field, List<String> fieldsList,
			int option, List<Field> fieldContainer) {
		if (null == field || null == fieldsList) {
			return;
		}
		// 如果成员是一个简单类型，或者成员出现循环现象
		if (isSimpleField(field, option, fieldContainer)
				|| isRecurseField(field, fieldContainer)) {
			// 如果设置了只使用简单成员时需要检查当前成员是不是简单类型
			if (!checkOption(ONLY_SIMPLE, option)
					|| (checkOption(ONLY_SIMPLE, option) && isSimpleField(
							field, option, fieldContainer))) {
				String fieldName = "";
				for (int i = 0; i < fieldContainer.size(); i++) {
					fieldName += fieldContainer.get(i).getName() + ".";
				}
				fieldsList.add(fieldName + field.getName());
			}
		} else {
			// 属性递归
			fieldContainer.add(field);
			Field[] fields = field.getType().getDeclaredFields();
			for (Field fld : fields) {
				getField(fld, fieldsList, option, fieldContainer);
			}
			fieldContainer.remove(field);
		}
	}

	public static void getObjectFields(Class<?> clz, List<String> fieldsList,
			int option) {
		if (null == clz || null == fieldsList) {
			return;
		}
		CLASS_TYPE = clz;
		// 记录原始对象Field，根据选项 INHERITED_FIELDS 来决定是不是包含父类成员
		List<Field> objFields = new ArrayList<Field>();

		// 按选项将当前需要用到的所有Filed先找到
		Class<?> tempClz = clz;
		do {
			Field[] fields = tempClz.getDeclaredFields();
			for (Field field : fields) {
				if (!checkDuplicateFieldName(objFields, field)) {
					objFields.add(field);
				}
			}
			tempClz = tempClz.getSuperclass();
		} while (needRecurseInherite(tempClz, option));

		List<Field> fieldContains = new ArrayList<Field>();
		for (Field field : objFields) {
			getField(field, fieldsList, option, fieldContains);
		}
	}

	/**
	 * 判断是否出现循环现象
	 * 
	 * @param field
	 * @param fieldContainer
	 * @return
	 */
	public static boolean isRecurseField(Field field, List<Field> fieldContainer) {
		for (Field container : fieldContainer) {
			if (field.getType().equals(container.getType())) {
				return true;
			}
		}
		if (field.getType().equals(CLASS_TYPE)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断属性是否足够简单
	 * 
	 * @return 如果选项指定Ongl,满足下面一项返回true: 没指定处理复杂类型选项 fieldClazz为基本类型
	 *         fieldClazz为String
	 */
	public static boolean isSimpleField(Field field, int option,
			List<Field> fieldContainer) {
		Class<?> fieldClazz = field.getType();
		boolean retVal = checkOption(SIMPLE, option)
				|| fieldClazz.isPrimitive() || fieldClazz.equals(String.class);
		return retVal;
	}

	/**
	 * 判断是否需要使用到父类，如果父类为Object则不使用
	 * 
	 * @return
	 */
	public static boolean needRecurseInherite(Class<?> curClazz, int option) {
		return (checkOption(INHERITE, option) && !curClazz.equals(Object.class));
	}
}
