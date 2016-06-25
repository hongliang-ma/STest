package com.aliyun.yuntest.protocols.http.message.resquest;

/**
 * @author mahongliang
 */
public class YunHttpByteArrayRequest extends YunHttpRequest {

	private static final long serialVersionUID = -8195424755876513250L;

	private byte[] bodyContent;

	public byte[] getBodyContent() {
		return bodyContent;
	}

	public void setBodyContent(byte[] bodyContent) {
		this.bodyContent = bodyContent;
	}
}
