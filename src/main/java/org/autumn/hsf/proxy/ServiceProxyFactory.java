/* 
 * @(#)ServiceProxyFactory.java    Created on 2013-7-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.autumn.hsf.HsfClient;
import org.autumn.hsf.handler.AsyncServiceRequestHandler;
import org.autumn.hsf.handler.SyncServiceRequestHandler;
import org.autumn.hsf.util.AsyncCallback;
import org.autumn.hsf.util.CallbackType;

/**
 * @author wanggf
 * @date 2013-7-24 下午9:24:28
 */
public class ServiceProxyFactory {

    private HsfClient hsfClient;

    private ServiceProxyFactory(HsfClient hsfClient) {
        this.hsfClient = hsfClient;
    }

    public static ServiceProxyFactory getFactoryInstance(HsfClient hsfClient) {
        return new ServiceProxyFactory(hsfClient);
    }

    /**
     * 同步代理
     * 
     * @param serviceInterface
     * @return
     */
    public <T> T wrapSyncProxy(final Class<T> serviceInterface) {
        return wrapSyncProxy4Service(hsfClient, serviceInterface);
    }

    /**
     * 异步代理
     * 
     * @param serviceInterface
     * @return
     */
    public <T> T wrapAsyncCallbackProxy(final Class<T> serviceInterface) {
        return wrapAsyncCallbackProxy(serviceInterface, null);
    }

    /**
     * 异步代理，带回调函数
     * 
     * @param serviceInterface
     * @param callback
     * @return
     */
    public <T> T wrapAsyncCallbackProxy(final Class<T> serviceInterface, AsyncCallback<Object> callback) {
        return this.wrapAsyncProxy4Service(hsfClient, serviceInterface, CallbackType.CALLBACK, callback);
    }

    /**
     * 异步代理，future方式
     * 
     * @param serviceInterface
     * @return
     */
    public <T> T wrapAsyncFutureProxy(final Class<T> serviceInterface) {
        return wrapAsyncProxy4Service(hsfClient, serviceInterface, CallbackType.FUTURE, null);
    }

    @SuppressWarnings("unchecked")
    private <T> T wrapSyncProxy4Service(HsfClient hsfClient, final Class<T> serviceInterface) {
        if (serviceInterface == null) {
            throw new IllegalArgumentException("serviceInterface不能为空");
        }
        else if (!serviceInterface.isInterface()) {
            throw new IllegalArgumentException("serviceInterface必须是一个接口");
        }

        InvocationHandler requestHandler = new SyncServiceRequestHandler(hsfClient, serviceInterface.getSimpleName());
        return (T) Proxy.newProxyInstance(getClassLoader(serviceInterface), new Class<?>[] { serviceInterface },
                requestHandler);
    }

    @SuppressWarnings("unchecked")
    private <T> T wrapAsyncProxy4Service(HsfClient hsfClient, final Class<T> serviceInterface, CallbackType type,
            final AsyncCallback<Object> callback) {
        if (serviceInterface == null) {
            throw new IllegalArgumentException("serviceInterface不能为空");
        }
        else if (!serviceInterface.isInterface()) {
            throw new IllegalArgumentException("serviceInterface必须是一个接口");
        }
        InvocationHandler requestHandler = new AsyncServiceRequestHandler(hsfClient, serviceInterface.getSimpleName(),
                type, callback);
        return (T) Proxy.newProxyInstance(getClassLoader(serviceInterface), new Class<?>[] { serviceInterface },
                requestHandler);
    }

    private static ClassLoader getClassLoader(Class<?> clazz) {
        if (clazz != null && clazz.getClassLoader() != null) {
            return clazz.getClassLoader();
        }

        if (Thread.currentThread().getContextClassLoader() != null) {
            return Thread.currentThread().getContextClassLoader();
        }

        return ClassLoader.getSystemClassLoader();
    }
}
