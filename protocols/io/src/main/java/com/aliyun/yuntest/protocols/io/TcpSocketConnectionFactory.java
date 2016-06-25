package com.aliyun.yuntest.protocols.io;

import java.io.IOException;
import java.net.Socket;

/**
 * @author mahongliang
 */
public interface TcpSocketConnectionFactory {
	
	TcpSocketConnection createSocketConnection(Socket socket) throws IOException;
	
}