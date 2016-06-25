package com.aliyun.yuntest.protocols.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.yuntest.framework.exception.YunException;
import com.aliyun.yuntest.framework.message.YunMessage;
import com.aliyun.yuntest.protocols.mina.handler.MessageIoHandler;

/**
 * @author mahongliang
 */
public abstract class MinaServer {

	private static Logger logger = LoggerFactory.getLogger(MinaServer.class);

	private NioSocketAcceptor acceptor = null;

	private final MinaFactory factory = MinaFactory.getInstance();

	private final String ip;

	private int port = 8080;

	public MinaServer(String ip, int port, boolean isSecure,
			MessageIoHandler handler, ProtocolCodecFactory codecFactory) {
		this.ip = ip;
		this.port = port;
		this.acceptor = factory.createAcceptor(codecFactory, handler, isSecure);
	}

	public NioSocketAcceptor getAcceptor() {
		return acceptor;
	}

	public abstract void send(YunMessage message, Object sessionToken);

	public void start() {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("Server bind ip: " + ip + ", listen port:" + port);
			}
			this.acceptor.bind(new InetSocketAddress(ip, port));
		} catch (IOException e) {
			logger.error("Server bind ip: " + ip + ", port:" + port
					+ " IOException:", e);
			throw new YunException(
					"Server bind port:" + port + " IOException:", e);
		}
	}

	public void stop() {
		if (acceptor != null) {
			acceptor.dispose();
			acceptor.unbind();
		}
	}
}
