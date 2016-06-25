package com.aliyun.yuntest.httpclient.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.yuntest.framework.exception.YunException;
import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.message.YunMessage;
import com.aliyun.yuntest.framework.message.YunSessionMessage;
import com.aliyun.yuntest.httpclient.YunHttpClient;
import com.aliyun.yuntest.protocols.http.message.response.YunHttpStringResponse;
import com.aliyun.yuntest.protocols.http.message.resquest.YunHttpByteArrayRequest;
import com.aliyun.yuntest.protocols.http.message.resquest.YunHttpRequest;
import com.aliyun.yuntest.protocols.http.message.resquest.YunHttpStringRequest;

/**
 * @author mahongliang
 */
public class YunSimpleHttpClient implements YunHttpClient {

	private static Logger logger = LoggerFactory
			.getLogger(YunSimpleHttpClient.class);

	/**
	 * 
	 * @param request
	 * @return
	 * @throws URISyntaxException
	 */
	private static URI buildUri(final YunHttpRequest request)
			throws URISyntaxException {
		URIBuilder builder = new URIBuilder();
		if (request.getIsSecure()) {
			builder.setScheme("https");
		} else {
			builder.setScheme("http");
		}
		builder.setHost(request.getHost()).setPath(request.getUri());

		if (request.getParamsMap() != null) {
			for (Map.Entry<String, String> entry : request.getParamsMap()
					.entrySet()) {
				builder.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return builder.build();
	}

	private IncomingMessageHandler incomingMessageHandler;

	private static int timeout = 12000;

	/**
	 * 
	 * @param httpResponse
	 * @throws ParseException
	 * @throws IOException
	 */
	private void buildResponse(final HttpResponse httpResponse)
			throws ParseException, IOException {
		// create response
		YunHttpStringResponse response = new YunHttpStringResponse(
				httpResponse.getStatusLine());

		// covert BufferedHeader to BasiceHeader, WTF why apache http client
		// designs two Header types ?
		Header[] headers = httpResponse.getAllHeaders();
		List<Header> headerList = new ArrayList<Header>();
		for (Header header : headers) {
			headerList
					.add(new BasicHeader(header.getName(), header.getValue()));
		}
		response.setHeaderList(headerList);

		// set response body
		HttpEntity entity = httpResponse.getEntity();
		response.setContent(EntityUtils.toString(entity));

		// convert to YunSessionMessage
		YunSessionMessage sessionMessage = new YunSessionMessage(response);
		incomingMessageHandler.incomingMessage(sessionMessage);
	}

	/**
	 * 
	 * @param request
	 * @throws HttpException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void doDelete(final YunHttpRequest request) throws HttpException,
			IOException, URISyntaxException {
		final HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				timeout);

		// create HttpDelete
		HttpDelete httpDelete = null;
		if (request.getUrl() == null) {
			httpDelete = new HttpDelete(buildUri(request));
		} else {
			httpDelete = new HttpDelete(request.getUrl());
		}
		// set headers
		httpDelete.setHeaders(request.getHeaders());
		// execute
		HttpResponse httpResponse = httpClient.execute(httpDelete);
		// create response
		buildResponse(httpResponse);
	}

	/**
	 * 
	 * @param request
	 * @throws HttpException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void doGet(final YunHttpRequest request) throws HttpException,
			IOException, URISyntaxException {
		final HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				timeout);

		// create HttpGet
		HttpGet httpGet = null;
		if (request.getUrl() == null) {
			httpGet = new HttpGet(buildUri(request));
		} else {
			httpGet = new HttpGet(request.getUrl());
		}
		// set headers
		httpGet.setHeaders(request.getHeaders());

		// execute
		HttpResponse httpResponse = httpClient.execute(httpGet);
		// create response
		buildResponse(httpResponse);
	}

	/**
	 * 
	 * @param request
	 * @throws URISyntaxException
	 * @throws HttpException
	 * @throws IOException
	 */
	public void doPost(final YunHttpRequest request) throws URISyntaxException,
			HttpException, IOException {

		final HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				timeout);

		// create HttpPost
		HttpPost httpPost = null;
		if (request.getUrl() == null) {
			httpPost = new HttpPost(buildUri(request));
		} else {
			httpPost = new HttpPost(request.getUrl());
		}
		// set headers
		httpPost.setHeaders(request.getHeaders());
		// set body
		if (request instanceof YunHttpStringRequest) {
			YunHttpStringRequest YunHttpStringRequest = (YunHttpStringRequest) request;
			StringEntity entity = new StringEntity(
					YunHttpStringRequest.getBodyContent(), "UTF-8");
			httpPost.setEntity(entity);
		} else {
			YunHttpByteArrayRequest YunHttpByteArrayRequest = (YunHttpByteArrayRequest) request;
			ByteArrayEntity entity = new ByteArrayEntity(
					YunHttpByteArrayRequest.getBodyContent());
			httpPost.setEntity(entity);
		}

		httpPost.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);

		// execute
		HttpResponse httpResponse = httpClient.execute(httpPost);
		// create response
		buildResponse(httpResponse);
	}

	/**
	 * 
	 * @param request
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void doPut(final YunHttpRequest request) throws URISyntaxException,
			ClientProtocolException, IOException {
		final HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				timeout);

		// create HttpPut
		HttpPut httpPut = null;
		if (request.getUrl() == null) {
			httpPut = new HttpPut(buildUri(request));
		} else {
			httpPut = new HttpPut(request.getUrl());
		}
		// set headers
		httpPut.setHeaders(request.getHeaders());
		// set body
		if (request instanceof YunHttpStringRequest) {
			YunHttpStringRequest YunHttpStringRequest = (YunHttpStringRequest) request;
			StringEntity entity = new StringEntity(
					YunHttpStringRequest.getBodyContent(), "UTF-8");
			httpPut.setEntity(entity);
		} else {
			YunHttpByteArrayRequest YunHttpByteArrayRequest = (YunHttpByteArrayRequest) request;
			ByteArrayEntity entity = new ByteArrayEntity(
					YunHttpByteArrayRequest.getBodyContent());
			httpPut.setEntity(entity);
		}

		httpPut.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);

		// execute
		HttpResponse httpResponse = httpClient.execute(httpPut);
		// create response
		buildResponse(httpResponse);
	}

	public void receive(YunMessage msg) {

	}

	public void send(final YunHttpRequest request, Object sessionToken) {

		new Thread() {
			@Override
			public void run() {
				try {
					if (HttpGet.METHOD_NAME.equalsIgnoreCase(request
							.getMethod())) {
						doGet(request);
						return;
					}

					if (HttpPost.METHOD_NAME.equalsIgnoreCase(request
							.getMethod())) {
						doPost(request);
						return;
					}

					if (HttpDelete.METHOD_NAME.equalsIgnoreCase(request
							.getMethod())) {
						doDelete(request);
						return;
					}

					if (HttpPut.METHOD_NAME.equalsIgnoreCase(request
							.getMethod())) {
						doPut(request);
						return;
					}

					throw new YunException("Unknown HttpRequest method.");

				} catch (Exception e) {
					logger.error("Send HttpRequest error!", e);
					throw new YunException("Send HttpRequest error!", e);
				}
			}
		}.start();
	}

	@Override
	public void setIncomingMessageHandler(
			IncomingMessageHandler incomingMessageHandler) {
		this.incomingMessageHandler = incomingMessageHandler;
	}

	public void setTestInteraction() {
	}
}
