/**
 * Alibaba.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.dagger.dtest.datadrive;

import org.apache.commons.lang3.StringUtils;

/**
 * Smoke用例匹配器
 * 
 * @author 马洪良
 * @version $Id: SmokeMatch.java, v 0.1 2014年3月13日 下午1:31:48 马洪良 Exp $
 */
public final class SmokeMatch {
	private String smokePrefixRegex = null;

	public SmokeMatch() {
	}

	public SmokeMatch(String smokePrefix) {
		this.smokePrefixRegex = smokePrefix;
	}

	/**
	 * 匹配caseId，如果不需要进行smock则认为匹配成功，直接返回true
	 * 
	 * @param caseId
	 * @return
	 */
	public boolean match(String caseId) {
		if (!needSmoke()) {
			return true;
		}
		if (StringUtils.isBlank(caseId)) {
			return false;
		}
		return caseId.trim().matches(smokePrefixRegex + ".*");
	}

	/**
	 * 是否需要进行smock，如果smock_test为空的，则不需要进行匹配
	 */
	public boolean needSmoke() {
		return StringUtils.isNotBlank(smokePrefixRegex);
	}

}
