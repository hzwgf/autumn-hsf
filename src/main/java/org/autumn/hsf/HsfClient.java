/* 
 * @(#)HsfClient.java    Created on 2013-7-22
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf;

import java.net.InetSocketAddress;
import java.util.HashMap;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.autumn.hsf.filter.HsfKeepAliveClientFilter;
import org.autumn.hsf.filter.HsfSerializerFilter;
import org.autumn.hsf.handler.HsfClientHandler;
import org.autumn.hsf.mina.HsfSession;

/**
 * @author wanggf
 * @date 2013-7-22 下午7:46:56
 */
public class HsfClient {

    private static final int CONNECT_TIMEOUT = 30;
    private String address;
    private int port;

    /**
     * 默认30s
     */
    private int connect_timeout = CONNECT_TIMEOUT;
    private boolean isReconnectWhenConnectFail;
    private NioSocketConnector connector;
    private HsfSession session;
    private HsfClientHandler handler;

    private HashMap<String, Object> opts = new HashMap<String, Object>();

    public HsfClient(String address, int port, boolean isReconnectWhenConnectFail) {
        this.address = address;
        this.port = port;
        this.isReconnectWhenConnectFail = isReconnectWhenConnectFail;
        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(connect_timeout);
        connector.getFilterChain().addLast("codec", new HsfSerializerFilter());

        // HsfKeepAliveFilter keepAlive = new HsfKeepAliveFilter();
        HsfKeepAliveClientFilter keepAlive = new HsfKeepAliveClientFilter();
        keepAlive.setRequestInterval(100);
        connector.getFilterChain().addLast("keepAlive", keepAlive);

        connector.getFilterChain().addLast("executorFilter", new ExecutorFilter());
        handler = new HsfClientHandler(this);
        connector.setHandler(handler);
    }

    public boolean connect() throws InterruptedException {
        ConnectFuture future = connector.connect(new InetSocketAddress(address, port));
        try {
            future.await();
            session = new HsfSession(future.getSession());
            return true;
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    public void setOpts(String key, Object value) {
        opts.put(key, value);
    }

    public Object getOpts(String key) {
        return opts.get(key);
    }

    public HsfSession getSession() {
        return session;
    }

    public HsfClientHandler getHandler() {
        return handler;
    }

    public boolean isReconnectWhenConnectFail() {
        return isReconnectWhenConnectFail;
    }

    public void setReconnectWhenConnectFail(boolean isReconnectWhenConnectFail) {
        this.isReconnectWhenConnectFail = isReconnectWhenConnectFail;
    }

}
