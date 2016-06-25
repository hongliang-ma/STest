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
import org.testng.annotations.Test;

import com.aliyun.yun.plugin.database.message.request.YunCountReq;
import com.aliyun.yun.plugin.database.message.response.YunCountResp;
import com.aliyun.yuntest.framework.YunTestTestNgBase;
import com.aliyun.yuntest.framework.interaction.TestInteraction;
import com.aliyun.yuntest.framework.message.YunSessionMessage;
import com.aliyun.yuntest.framework.plugin.Plugin;
import com.aliyun.yuntest.protocols.http.message.response.YunHttpStringResponse;
import com.aliyun.yuntest.protocols.http.message.resquest.YunHttpRequest;
import com.aliyun.yuntest.protocols.http.message.resquest.YunHttpStringRequest;

public class TestNGDemo extends YunTestTestNgBase {

	private static final Logger logger = LoggerFactory
			.getLogger(TestNGDemo.class);

	Plugin httpServer;

	Plugin httpClient;

	Plugin dbClient;

	@Override
	public void setUp(TestInteraction testInteraction) {
		logger.info("TestHttpServer setUp");
		httpServer = testInteraction.getPlugin("httpserver");
		httpClient = testInteraction.getPlugin("httpclient");
		dbClient = testInteraction.getPlugin("database");
	}

	@Override
	public void tearDown(TestInteraction testInteraction) {
		// TODO Auto-generated method stub

	}

	@Test
	public void testStart() {
		logger.info("TestHttp testStart ");
		interaciton.beforeTest(interaciton);

		// create request
		YunHttpRequest clientHttpRequest = new YunHttpStringRequest();
		clientHttpRequest.setMethod(HttpGet.METHOD_NAME);
		clientHttpRequest.setUrl("http://localhost:8080");

		toJson(clientHttpRequest);
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
		YunHttpStringResponse.setContent("It is just a test.");

		// server send http response
		httpServer.send(YunHttpStringResponse, sessionMessage);

		YunCountReq YunCountReq = new YunCountReq();
		YunCountReq.setSql("select count(*) from city");
		dbClient.send(YunCountReq);

		YunCountResp resp = new YunCountResp();
		resp.setCount(Integer.valueOf(600));
		dbClient.receive(resp);

		// Client receive response
		sessionMessage = httpClient.receive(YunHttpStringResponse);
		YunHttpStringResponse = (YunHttpStringResponse) sessionMessage
				.getMessage();
		logger.info(YunHttpStringResponse.getStatusLine().toString());
		logger.info(YunHttpStringResponse.getContent());

		interaciton.afterTest(interaciton);
	}

}
