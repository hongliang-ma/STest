package com.aliyun.yuntest.protocols.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.aliyun.yuntest.framework.exception.YunException;

public abstract class NettyServer {

	private final int port;

	protected ServerBootstrap bootstrap;

	private ChannelPipelineFactory channelPipelineFactory;

	public NettyServer(int port) {
		this.port = port;
	}

	public void run() {

		// check ChannelPipelineFactory
		if (channelPipelineFactory == null) {
			throw new YunException("ChannelPipelineFactory is null !");
		}

		// Configure the server.
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));

		// Enable TCP_NODELAY to handle pipelined requests without latency.
		bootstrap.setOption("child.tcpNoDelay", true);

		// Set up the event pipeline factory.
		bootstrap.setPipelineFactory(channelPipelineFactory);

		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(port));

	}

	public void setChannelPipelineFactory(
			ChannelPipelineFactory channelPipelineFactory) {
		this.channelPipelineFactory = channelPipelineFactory;
	}

	protected void stop() {
		if (bootstrap != null) {
			bootstrap.shutdown();
		}

		bootstrap = null;
	}

}
