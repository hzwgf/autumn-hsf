/* 
 * @(#)HsfServer.java    Created on 2013-7-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;

import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.autumn.hsf.annotation.RemoteServiceContract;
import org.autumn.hsf.filter.HsfKeepAliveFilter;
import org.autumn.hsf.filter.HsfSerializerFilter;
import org.autumn.hsf.handler.HsfServerHandler;

/**
 * @author wanggf
 * @date 2013-7-24 下午5:37:22
 */
public class HsfServer {
    private int port;

    private static LinkedHashMap<String, Object> services = new LinkedHashMap<String, Object>();

    public HsfServer(int port) throws IOException {
        this.port = port;
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("codec", new HsfSerializerFilter());

        HsfKeepAliveFilter keepAlive = new HsfKeepAliveFilter();
        keepAlive.setRequestInterval(100);
        acceptor.getFilterChain().addLast("keepAlive", keepAlive);

        acceptor.getFilterChain().addLast("executorFilter", new ExecutorFilter());

        acceptor.setHandler(new HsfServerHandler());
        // registerService(new TestServiceImpl());
        acceptor.bind(new InetSocketAddress(port));
    }

    public void registerService(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces == null) {
            return;
        }
        for (Class<?> clazz : interfaces) {
            RemoteServiceContract ann = clazz.getAnnotation(RemoteServiceContract.class);
            if (ann == null) {
                continue;
            }
            services.put(clazz.getSimpleName(), service);
        }
    }

    public static Object findService(String name) {
        return services.get(name);
    }

}
