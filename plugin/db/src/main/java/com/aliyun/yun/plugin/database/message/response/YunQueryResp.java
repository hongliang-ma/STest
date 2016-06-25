package com.aliyun.yun.plugin.database.message.response;

import java.util.List;
import java.util.Map;

import com.aliyun.yuntest.framework.message.YunMessage;

public class YunQueryResp implements YunMessage {

	/**  */
	private static final long serialVersionUID = 7986524853448134447L;
	private List<Map<String, Object>> list;

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

}
