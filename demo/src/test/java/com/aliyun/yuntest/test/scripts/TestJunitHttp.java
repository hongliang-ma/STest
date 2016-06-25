package com.aliyun.yuntest.test.scripts;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.EnglishReasonPhraseCatalog;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.yuntest.framework.YunTestJunitBase;
import com.aliyun.yuntest.framework.interaction.TestInteraction;
import com.aliyun.yuntest.framework.message.YunSessionMessage;
import com.aliyun.yuntest.framework.plugin.Plugin;
import com.aliyun.yuntest.protocols.http.message.response.YunHttpStringResponse;
import com.aliyun.yuntest.protocols.http.message.resquest.YunHttpRequest;
import com.aliyun.yuntest.protocols.http.message.resquest.YunHttpStringRequest;

public class TestJunitHttp extends YunTestJunitBase {

	private static final Logger logger = LoggerFactory
			.getLogger(TestJunitHttp.class);

	Plugin httpServer;

	Plugin httpClient;

	@Override
	public void setUp(TestInteraction testInteraction) {
		logger.info("TestHttpServer setUp");
		httpServer = testInteraction.getPlugin("httpserver");
		httpClient = testInteraction.getPlugin("httpclient");
	}

	@Override
	public void tearDown(TestInteraction testInteraction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void testStart(TestInteraction testInteraction) {
		logger.info("TestHttp testStart ");

		// create request
		YunHttpRequest clientHttpRequest = new YunHttpStringRequest();
		clientHttpRequest.setMethod(HttpGet.METHOD_NAME);
		clientHttpRequest.setUrl("http://localhost:8080");
		// send request
		httpClient.send(clientHttpRequest);

		// create request
		YunHttpRequest httpRequest = new YunHttpStringRequest();
		httpRequest.setMethod(HttpGet.METHOD_NAME);
		httpRequest.setUri("/");

		// server receive http request
		YunSessionMessage sessionMessage = httpServer.receive(httpRequest);

		// create server http reponse
		BasicStatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1,
				HttpStatus.SC_OK,
				EnglishReasonPhraseCatalog.INSTANCE.getReason(HttpStatus.SC_OK,
						null));
		YunHttpStringResponse YunHttpStringResponse = new YunHttpStringResponse(
				statusLine);
		YunHttpStringResponse.addHeader(new BasicHeader(
				HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8"));
		YunHttpStringResponse.setContent("It is just a test.\r\n\r\n");

		// server send http response
		httpServer.send(YunHttpStringResponse, sessionMessage);

		// Client receive response
		sessionMessage = httpClient.receive(YunHttpStringResponse);
		YunHttpStringResponse = (YunHttpStringResponse) sessionMessage
				.getMessage();
		logger.info(YunHttpStringResponse.getStatusLine().toString());
		logger.info(YunHttpStringResponse.getContent());
	}

}
