package com.aliyun.yuntest.protocols.http.message.response;

import org.apache.http.StatusLine;

/**
 * @author mahongliang
 */
public class YunHttpStringResponse extends YunHttpResponse {

	private static final long serialVersionUID = 1108220325893945583L;

	private String content;
	
	public YunHttpStringResponse(StatusLine statusLine){
		this.statusLine = statusLine;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
