package com.aliyun.yuntest.plugin.httpserver.handler;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.message.YunSessionMessage;

public class YunSimpleHttpHandler extends SimpleChannelUpstreamHandler {

	private IncomingMessageHandler incomingMessageHandler;

	public IncomingMessageHandler getIncomingMessageHandler() {
		return incomingMessageHandler;
	}

	/**
	 * handle http request and give response
	 */
	public YunSessionMessage handle(final HttpRequest request,
			final HttpResponse response, final HttpContext context)
			throws HttpException, IOException {
		return null;

	}

	public void setIncomingMessageHandler(
			IncomingMessageHandler incomingMessageHandler) {
		this.incomingMessageHandler = incomingMessageHandler;
	}

}
