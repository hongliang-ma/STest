package com.aliyun.yuntest.protocols.io.server;


import java.io.IOException;
import java.net.Socket;

import com.aliyun.yuntest.protocols.io.TcpSocketConnection;
import com.aliyun.yuntest.protocols.io.TcpSocketConnectionFactory;


/**
 * @author mahongliang
 */
public class FactoryTcpServer extends TcpServer {

    private TcpSocketConnectionFactory connectionFactory;

    public FactoryTcpServer(String hostname, int port, TcpSocketConnectionFactory connectionFactory) {
        super(hostname, port);
        this.connectionFactory = connectionFactory;
    }

    protected TcpSocketConnection createSocketConnection(Socket socket) throws IOException {
        return connectionFactory.createSocketConnection(socket);
    }

}
