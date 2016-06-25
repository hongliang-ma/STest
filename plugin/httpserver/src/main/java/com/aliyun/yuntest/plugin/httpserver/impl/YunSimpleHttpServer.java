package com.aliyun.yuntest.plugin.httpserver.impl;

import javax.net.ssl.SSLContext;

import org.apache.http.protocol.HttpRequestHandler;

import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.message.YunMessage;
import com.aliyun.yuntest.plugin.httpserver.YunHttpServer;
import com.aliyun.yuntest.plugin.httpserver.handler.YunSimpleHttpHandler;
import com.aliyun.yuntest.protocols.http.Authenticator;
import com.aliyun.yuntest.protocols.http.HttpServer;

public class YunSimpleHttpServer extends HttpServer implements YunHttpServer {

	private YunSimpleHttpHandler httpRequestHandler;

	public YunSimpleHttpServer(String host, int port, boolean secure, SSLContext sslContext, boolean blocking, int threadPoolSize, HttpRequestHandler handler, Authenticator authenticator, int soTimeout, int soBufferSizeInBytes) throws Exception {
		super(host, port, secure, sslContext, blocking, threadPoolSize, handler, authenticator, soTimeout, soBufferSizeInBytes);
		// TODO Auto-generated constructor stub
	}

	public void start() throws Exception {
		super.start();
	}

	public void stop() {
		super.stop();
	}

	public void setIncomingMessageHandler(IncomingMessageHandler incomingMessageHandler) {
		httpRequestHandler.setIncomingMessageHandler(incomingMessageHandler);
	}

	@Override
	public void send(YunMessage message, Object sessionToken) {
		// TODO Auto-generated method stub
		
	}
}
