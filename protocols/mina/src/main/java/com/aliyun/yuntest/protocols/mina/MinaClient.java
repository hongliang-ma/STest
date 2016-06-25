package com.aliyun.yuntest.protocols.mina;

import java.net.InetSocketAddress;

import javax.net.ssl.SSLContext;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.yuntest.framework.exception.YunException;
import com.aliyun.yuntest.protocols.mina.ssl.TrustManagerFactoryImpl;

/**
 * @author mahongliang
 */
public class MinaClient {

	private static Logger logger = LoggerFactory.getLogger(MinaClient.class);

	private static SslFilter createSSLFilter() throws Exception {
		SSLContext context = SSLContext
				.getInstance(MinaFactory.DEFAULT_SSL_PROTOCOL);
		context.init(null, TrustManagerFactoryImpl.X509_MANAGERS, null);
		SslFilter sslFilter = new SslFilter(context);
		sslFilter.setUseClientMode(true);
		return sslFilter;
	}

	private final MinaFactory factory = MinaFactory.getInstance();
	private NioSocketConnector connector;
	private IoHandler handler;
	private ProtocolCodecFactory codecFactory;
	private IoSession session;
	private String ip;
	private int port;

	private boolean isSecure;

	public static final String SSL_FILTER = "SSL";

	private static long CONNECT_TIMEOUT = 3000;

	public MinaClient() {
	}

	public ProtocolCodecFactory getCodecFactory() {
		return codecFactory;
	}

	public IoHandler getHandler() {
		return handler;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public boolean isSecure() {
		return isSecure;
	}

	public void send(Object message, Object sessionToken) {
		session.write(message);
	}

	public void setCodecFactory(ProtocolCodecFactory codecFactory) {
		this.codecFactory = codecFactory;
	}

	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setSecure(boolean isSecure) {
		this.isSecure = isSecure;
	}

	public void shutdown() {
		session = null;
	}

	public void start() {
		logger.info("client connect to ip: " + ip + ", port: " + port);

		try {
			connector = factory.createConnector(handler, codecFactory);
			ConnectFuture future = connector.connect(new InetSocketAddress(ip,
					port));
			future.awaitUninterruptibly(CONNECT_TIMEOUT);
			session = future.await().getSession();

			if (isSecure) {
				session.getFilterChain().addLast(SSL_FILTER, createSSLFilter());
			}
		} catch (InterruptedException e) {
			final String errorInfo = "client connect to " + ip + ":" + port
					+ "fail !";
			logger.error(errorInfo);
			throw new YunException(errorInfo, e);
		} catch (Exception e) {
			final String errorInfo = "client ssl connect to " + ip + ":" + port
					+ "fail !";
			logger.error(errorInfo);
			throw new YunException(errorInfo, e);
		}
	}

	public void stop() {
		if (logger.isDebugEnabled()) {
			logger.debug("Client stop...");
			logger.debug("Shutdown of connection towards " + ip + ":" + port);
		}

		if (session != null) {
			session.close(false).awaitUninterruptibly(CONNECT_TIMEOUT);
			session = null;
		}
	}
}
