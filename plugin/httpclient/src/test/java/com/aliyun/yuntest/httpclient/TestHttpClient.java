package com.aliyun.yuntest.httpclient;

import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.EnglishReasonPhraseCatalog;
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

public class TestHttpClient extends YunTestJunitBase {

	private static final Logger logger = LoggerFactory
			.getLogger(TestHttpClient.class);

	Plugin httpclient;

	@Override
	public void setUp(TestInteraction testInteraction) {
		logger.info("TestHttpClient setUp");
		httpclient = testInteraction.getPlugin("httpclient");
	}

	@Override
	public void tearDown(TestInteraction testInteraction) {
		logger.info("TestPing tearDown ");
	}

	@Override
	public void testStart(TestInteraction testInteraction) {
		logger.info("TestPing testStart ");

		// create request
		YunHttpRequest httpRequest = new YunHttpStringRequest();
		httpRequest.setMethod(HttpGet.METHOD_NAME);
		httpRequest.setUrl("http://www.baidu.com");
		// httpRequest.setUrl("http://localhost:8080");
		// send request
		httpclient.send(httpRequest);

		// create response
		YunHttpStringResponse YunHttpStringResponse = null;

		BasicStatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1,
				HttpStatus.SC_ACCEPTED,
				EnglishReasonPhraseCatalog.INSTANCE.getReason(HttpStatus.SC_OK,
						null));
		YunHttpStringResponse = new YunHttpStringResponse(statusLine);

		// receive response
		YunSessionMessage sessionMessage = httpclient
				.receive(YunHttpStringResponse);
		YunHttpStringResponse = (YunHttpStringResponse) sessionMessage
				.getMessage();
		logger.info(YunHttpStringResponse.getStatusLine().toString());
		logger.info(YunHttpStringResponse.getContent());
	}

}
