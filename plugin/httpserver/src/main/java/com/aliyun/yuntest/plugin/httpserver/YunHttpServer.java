package com.aliyun.yuntest.plugin.httpserver;

import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.message.YunMessage;

public interface YunHttpServer {

	void setIncomingMessageHandler(IncomingMessageHandler incomingMessageHandler);

	void send(final YunMessage message, final Object sessionToken);

	void start() throws Exception;

	void stop();
}
