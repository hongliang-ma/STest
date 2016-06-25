package com.aliyun.yuntest.protocols.http.message.resquest;

/**
 * @author mahongliang
 */
public class YunHttpStringRequest extends YunHttpRequest {

	private static final long serialVersionUID = -4953417104391938132L;
	private String bodyContent;

	public String getBodyContent() {
		return bodyContent;
	}

	public void setBodyContent(String bodyContent) {
		this.bodyContent = bodyContent;
	}

}
