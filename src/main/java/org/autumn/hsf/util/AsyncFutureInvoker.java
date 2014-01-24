/* 
 * @(#)AsyncFutureInvoker.java    Created on 2013-9-1
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.util;

import org.autumn.hsf.mina.InvocationFuture;

/**
 * @author wanggf
 * @date 2013-9-1 下午3:48:24
 */
public abstract class AsyncFutureInvoker<T> {

    private Class<T> serviceInterface;

    public AsyncFutureInvoker(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    protected abstract void invokeService();

    public InvocationFuture<Object> invoke() {
        invokeService();
        return (InvocationFuture<Object>) TLUtils.getData(InvocationFuture.class);
    }
}
