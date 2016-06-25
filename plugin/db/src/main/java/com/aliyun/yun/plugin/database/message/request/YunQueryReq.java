package com.aliyun.yun.plugin.database.message.request;

import com.aliyun.yuntest.framework.message.YunMessage;

public class YunQueryReq implements YunMessage {

	/**  */
	private static final long serialVersionUID = 7789894030804989156L;
	private String sql;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}