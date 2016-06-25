package com.aliyun.yun.plugin.database.message.request;

import com.aliyun.yuntest.framework.message.YunMessage;

public class YunExcuteReq implements YunMessage {

	/**  */
	private static final long serialVersionUID = 262164242901662832L;
	private String sql;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
