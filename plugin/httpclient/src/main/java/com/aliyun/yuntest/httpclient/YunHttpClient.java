package com.aliyun.yuntest.httpclient;

import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.protocols.http.message.resquest.YunHttpRequest;

public interface YunHttpClient {

	void send(final YunHttpRequest request, Object sessionToken);

	void setIncomingMessageHandler(IncomingMessageHandler incomingMessageHandler);
}
