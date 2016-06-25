/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2008 All Rights Reserved.
 */
package com.aliyun.yun.plugin.database.utils;

import java.math.BigDecimal;
import java.util.HashMap;

public class DataMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 9164967756576939731L;

	public static String convertKey(String key) {
		return key == null || key.length() == 0 ? null : key.toUpperCase();
	}

	public int getIntValue(String key) {
		key = convertKey(key);
		String stringValue = this.getStringValue(key);
		int value = Integer.parseInt(stringValue);

		return value;
	}

	public long getLongValue(String key) {
		key = convertKey(key);
		String stringValue = this.getStringValue(key);
		long value = Long.parseLong(stringValue);

		return value;
	}

	public String getStringValue(String key) {
		key = convertKey(key);
		String value = null;
		if (this.containsKey(key)) {
			Object obj = this.get(key);
			if (null == obj) {
				return null;
			} else if (obj instanceof BigDecimal) {
				value = ((BigDecimal) obj).toString();
			} else {
				value = String.valueOf(obj);
			}
		}

		return value;
	}
}
