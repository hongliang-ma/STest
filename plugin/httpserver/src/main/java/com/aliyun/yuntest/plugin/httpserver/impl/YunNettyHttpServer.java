package com.aliyun.yuntest.plugin.httpserver.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.yuntest.framework.exception.YunException;
import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.message.YunMessage;
import com.aliyun.yuntest.framework.message.YunSessionMessage;
import com.aliyun.yuntest.plugin.httpserver.YunHttpServer;
import com.aliyun.yuntest.plugin.httpserver.netty.HttpDefaultServerPipelineFactory;
import com.aliyun.yuntest.protocols.netty.NettyServer;

public class YunNettyHttpServer extends NettyServer implements YunHttpServer {

	private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

	public YunNettyHttpServer(int port) {
		super(port);

	}

	@Override
	public void setIncomingMessageHandler(IncomingMessageHandler incomingMessageHandler) {
		this.setChannelPipelineFactory(new HttpDefaultServerPipelineFactory(incomingMessageHandler));
	}

	@Override
	public void start() throws Exception {
		this.run();
	}

	@Override
	public void stop() {
		super.stop();
	}

	@Override
	public void send(YunMessage message, Object sessionToken) {
		YunSessionMessage sessionMessage = (YunSessionMessage) sessionToken;
		try {
			sessionMessage.sendingMessage(message);
		} catch (InterruptedException e) {
			final String errorInfo = "HTTP Server sends message error!";
			logger.error(errorInfo, e);
			throw new YunException(errorInfo, e);
		}
	}

}
