package com.aliyun.yuntest.plugin.httpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.yuntest.framework.command.YunCommand;
import com.aliyun.yuntest.framework.exception.YunException;
import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.interaction.TestInteraction;
import com.aliyun.yuntest.framework.message.YunMessage;
import com.aliyun.yuntest.framework.message.YunSessionMessage;
import com.aliyun.yuntest.framework.plugin.Plugin;

public class HttpServerPlugin implements Plugin {

	private static Logger logger = LoggerFactory
			.getLogger(HttpServerPlugin.class);

	private TestInteraction testInteraction;
	private IncomingMessageHandler incomingMessageHandler;

	private YunHttpServer httpServer;

	public void execute(YunCommand command) {
		// TODO Auto-generated method stub

	}

	public YunSessionMessage receive() {
		return incomingMessageHandler.receive();
	}

	public YunSessionMessage receive(YunMessage message) {
		return incomingMessageHandler.receive(message);
	}

	public void send(YunMessage message) {
		send(message, null);
	}

	public void send(YunMessage message, Object sessionToken) {
		if (message == null) {
			throw new AssertionError("YunMessage is null.");
		}
		logger.info("------> HTTP server sends {} message------>",
				message.getClass());

		httpServer.send(message, sessionToken);
	}

	public void setHttpServer(YunHttpServer httpServer) {
		this.httpServer = httpServer;
	}

	public void setIncomingMessageHandler(IncomingMessageHandler handler) {
		this.incomingMessageHandler = handler;
	}

	public void setTestInteraction(TestInteraction testInteraction) {
		this.testInteraction = testInteraction;
	}

	public void start() {
		logger.info("HTTP server start...");
		incomingMessageHandler.setTestInteraction(testInteraction);
		httpServer.setIncomingMessageHandler(incomingMessageHandler);

		try {
			httpServer.start();
		} catch (Exception e) {
			logger.error("start HTTP Server error!", e);
			throw new YunException("start HTTP Server error!", e);
		}
	}

	public void stop() {
		httpServer.stop();
	}

}
