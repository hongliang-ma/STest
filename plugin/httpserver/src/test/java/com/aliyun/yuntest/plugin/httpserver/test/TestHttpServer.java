package com.aliyun.yuntest.plugin.httpserver.test;

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

public class TestHttpServer extends YunTestJunitBase {

	private static final Logger logger = LoggerFactory
			.getLogger(TestHttpServer.class);

	Plugin httpServer;

	@Override
	public void setUp(TestInteraction testInteraction) {
		logger.info("TestHttpServer setUp");
		httpServer = testInteraction.getPlugin("httpserver");
	}

	@Override
	public void tearDown(TestInteraction testInteraction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void testStart(TestInteraction testInteraction) {
		logger.info("TestHttpServer testStart ");
		// create request
		YunHttpRequest httpRequest = new YunHttpStringRequest();
		httpRequest.setMethod(HttpGet.METHOD_NAME);
		httpRequest.setUri("/");

		// receive http request
		YunSessionMessage sessionMessage = httpServer.receive(httpRequest);

		BasicStatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1,
				HttpStatus.SC_OK,
				EnglishReasonPhraseCatalog.INSTANCE.getReason(HttpStatus.SC_OK,
						null));
		YunHttpStringResponse httpResponse = new YunHttpStringResponse(
				statusLine);
		httpResponse.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE,
				"text/plain; charset=UTF-8"));
		httpResponse.setContent("It is just a test.");

		// send http response
		httpServer.send(httpResponse, sessionMessage);

		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
