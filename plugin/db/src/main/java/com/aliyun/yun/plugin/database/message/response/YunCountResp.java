package com.aliyun.yun.plugin.database.message.response;

import com.aliyun.yuntest.framework.message.YunMessage;

public class YunCountResp implements YunMessage {

	/**  */
	private static final long serialVersionUID = 2875804643209677540L;
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
