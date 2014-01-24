/* 
 * @(#)SyncServiceRequestHandler.java    Created on 2013-8-31
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.autumn.hsf.HsfClient;
import org.autumn.hsf.pojo.RemoteServiceObject;

/**
 * @author wanggf
 * @date 2013-8-31 下午2:29:19
 */
public class SyncServiceRequestHandler implements InvocationHandler {

    private HsfClient hsfClient;
    private String serviceName;

    public SyncServiceRequestHandler(HsfClient hsfClient, String serviceName) {
        this.hsfClient = hsfClient;
        this.serviceName = serviceName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RemoteServiceObject rso = new RemoteServiceObject();
        rso.setService(serviceName);
        rso.setMethod(method.getName());
        rso.setArgs(args);
        return hsfClient.getSession().writeSync(rso);
    }
}
