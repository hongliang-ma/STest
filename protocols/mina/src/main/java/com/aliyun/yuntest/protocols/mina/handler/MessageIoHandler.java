package com.aliyun.yuntest.protocols.mina.handler;


import org.apache.mina.core.service.IoHandlerAdapter;

import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.message.YunMessage;

/**
 * @author mahongliang
 */
public class MessageIoHandler extends IoHandlerAdapter {

    protected IncomingMessageHandler handler;

    protected void send(YunMessage message) {

    }

    public IncomingMessageHandler getHandler() {
        return handler;
    }

    public void setHandler(IncomingMessageHandler handler) {
        this.handler = handler;
    }
}
