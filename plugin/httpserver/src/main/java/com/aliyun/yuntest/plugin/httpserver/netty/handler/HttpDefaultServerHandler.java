package com.aliyun.yuntest.plugin.httpserver.netty.handler;

import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.message.YunSessionMessage;
import com.aliyun.yuntest.plugin.utils.HttpMessageUtil;
import com.aliyun.yuntest.protocols.http.message.response.YunHttpStringResponse;

public class HttpDefaultServerHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(HttpDefaultServerHandler.class);

	private static void send100Continue(MessageEvent e) {
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, CONTINUE);
		e.getChannel().write(response);
	}

	private boolean readingChunks;

	private final IncomingMessageHandler incomingMessageHandler;

	public HttpDefaultServerHandler(
			IncomingMessageHandler incomingMessageHandler) {
		this.incomingMessageHandler = incomingMessageHandler;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		logger.debug("HttpDefaultServerHandler messageReceived");

		if (!readingChunks) {
			HttpRequest request = (HttpRequest) e.getMessage();
			logger.debug("HttpRequest messageReceived, uri= {}",
					request.getUri());

			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(e);
			}

			isKeepAlive(request);

			final YunSessionMessage sessionMessage = new YunSessionMessage(
					HttpMessageUtil.convert(request));
			incomingMessageHandler.incomingMessage(sessionMessage);

			final MessageEvent messageEvent = e;
			new Thread() {
				@Override
				public void run() {
					logger.debug("wait for setting http response...");
					YunHttpStringResponse YunHttpStringResponse;
					try {
						YunHttpStringResponse = (YunHttpStringResponse) sessionMessage
								.getSendingMessage(60, TimeUnit.SECONDS);
						// Write the response
						if (YunHttpStringResponse != null) {
							logger.debug("write http response...");
							ChannelFuture future = messageEvent.getChannel()
									.write(HttpMessageUtil
											.convert(YunHttpStringResponse));
							future.addListener(ChannelFutureListener.CLOSE);
						}
					} catch (InterruptedException e) {
						logger.error("Server send http response error!");
					}

				}
			}.start();

			// Close the non-keep-alive connection after the write operation is
			// done.
			// if (!keepAlive) {
			// future.addListener(ChannelFutureListener.CLOSE);
			// }else{
			// logger.debug("http connection keep alive...");
			// }

		}

		// TODO: handle http chunk

		// else {
		// HttpChunk chunk = (HttpChunk) e.getMessage();
		// if (chunk.isLast()) {
		// readingChunks = false;
		// buf.append("END OF CONTENT\r\n");
		//
		// HttpChunkTrailer trailer = (HttpChunkTrailer) chunk;
		// if (!trailer.getHeaderNames().isEmpty()) {
		// buf.append("\r\n");
		// for (String name : trailer.getHeaderNames()) {
		// for (String value : trailer.getHeaders(name)) {
		// buf.append("TRAILING HEADER: " + name + " = " + value + "\r\n");
		// }
		// }
		// buf.append("\r\n");
		// }
		// writeResponse(e);
		// } else {
		// buf.append("CHUNK: " + chunk.getContent().toString(CharsetUtil.UTF_8)
		// + "\r\n");
		// }
		// }
	}
}
