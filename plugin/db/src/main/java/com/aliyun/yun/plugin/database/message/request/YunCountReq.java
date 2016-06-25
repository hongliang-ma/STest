package com.aliyun.yun.plugin.database.message.request;

import com.aliyun.yuntest.framework.message.YunMessage;

public class YunCountReq implements YunMessage {

	/**  */
	private static final long serialVersionUID = -7088542983996551565L;
	private String sql;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
