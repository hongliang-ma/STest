package com.aliyun.yuntest.httpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.yuntest.framework.command.YunCommand;
import com.aliyun.yuntest.framework.exception.YunException;
import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.interaction.TestInteraction;
import com.aliyun.yuntest.framework.message.YunMessage;
import com.aliyun.yuntest.framework.message.YunSessionMessage;
import com.aliyun.yuntest.framework.plugin.Plugin;
import com.aliyun.yuntest.protocols.http.message.resquest.YunHttpRequest;

public class HttpClientPlugin implements Plugin {

	private static Logger logger = LoggerFactory
			.getLogger(HttpClientPlugin.class);
	private TestInteraction testInteraction;
	private IncomingMessageHandler incomingMessageHandler;
	private YunHttpClient httpClient;

	public void execute(YunCommand command) {
		// TODO Auto-generated method stub

	}

	public YunHttpClient getHttpClient() {
		return httpClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.Yun.free.framework.plugin.Plugin#recevie()
	 */
	@Override
	public YunSessionMessage receive() {
		return incomingMessageHandler.receive();
	}

	@Override
	public YunSessionMessage receive(YunMessage message) {
		if (message == null) {
			throw new AssertionError("YunMessage is null.");
		}

		return incomingMessageHandler.receive(message);
	}

	@Override
	public void send(YunMessage message) {
		send(message, null);
	}

	@Override
	public void send(YunMessage message, Object sessionToken) {
		if (message == null) {
			throw new AssertionError("YunMessage is null.");
		}

		logger.info(" ------> YunHttpClient is sending : {} message------>",
				message.getClass());
		if (message instanceof YunHttpRequest) {
			YunHttpRequest request = (YunHttpRequest) message;
			httpClient.send(request, sessionToken);
		} else {
			throw new YunException(
					"Only YunHttpRequest are accepted by YunHttpClient!");
		}
	}

	public void setHttpClient(YunHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public void setIncomingMessageHandler(
			IncomingMessageHandler incomingMessageHandler) {
		this.incomingMessageHandler = incomingMessageHandler;
	}

	@Override
	public void setTestInteraction(TestInteraction testInteraction) {
		this.testInteraction = testInteraction;
	}

	@Override
	public void start() {
		incomingMessageHandler.setTestInteraction(testInteraction);
		httpClient.setIncomingMessageHandler(incomingMessageHandler);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

}
