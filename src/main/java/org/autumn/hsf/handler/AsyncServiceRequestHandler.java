/* 
 * @(#)AsyncServiceReqeustHandler.java    Created on 2013-9-1
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.autumn.hsf.HsfClient;
import org.autumn.hsf.pojo.RemoteServiceObject;
import org.autumn.hsf.util.AsyncCallback;
import org.autumn.hsf.util.CallbackType;
import org.autumn.hsf.util.ReflectionUtils;

/**
 * @author wanggf
 * @date 2013-9-1 下午2:50:35
 */
public class AsyncServiceRequestHandler implements InvocationHandler {

    private HsfClient hsfClient;

    private String serviceName;

    private CallbackType type;

    private AsyncCallback<Object> callback;

    /**
     * @param hsfClient
     * @param serviceName
     * @param callback
     */
    public AsyncServiceRequestHandler(HsfClient hsfClient, String serviceName, CallbackType type,
            AsyncCallback<Object> callback) {
        super();
        this.hsfClient = hsfClient;
        this.serviceName = serviceName;
        this.type = type;
        this.callback = callback;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RemoteServiceObject rso = new RemoteServiceObject();
        rso.setService(serviceName);
        rso.setMethod(method.getName());
        rso.setArgs(args);
        if (type.equals(CallbackType.CALLBACK)) {
            hsfClient.getSession().writeAsyncCallback(rso, callback);
        }
        else if (type.equals(CallbackType.FUTURE)) {
            hsfClient.getSession().writeAsyncFuture(rso);
        }

        return ReflectionUtils.getDefaultValue(method.getReturnType());
    }

}
