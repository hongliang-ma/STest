package com.aliyun.yuntest.protocols.io.client;


import java.io.IOException;
import java.net.Socket;

import com.aliyun.yuntest.protocols.io.TcpSocketConnection;
import com.aliyun.yuntest.protocols.io.TcpSocketConnectionFactory;

/**
 * @author mahongliang
 */
public class FactoryTcpClient extends TcpClient {

    private TcpSocketConnectionFactory connectionFactory;

    public FactoryTcpClient(String hostname, int port, TcpSocketConnectionFactory connectionFactory) {
        super(hostname, port);
        this.connectionFactory = connectionFactory;
    }

    protected TcpSocketConnection createSocketConnection(Socket socket) throws IOException {
        return connectionFactory.createSocketConnection(socket);
    }

}
