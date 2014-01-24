/* 
 * @(#)HsfSession.java    Created on 2013-8-31
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.mina;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.mina.core.session.IoSession;
import org.autumn.hsf.pojo.RemoteServiceObject;
import org.autumn.hsf.util.AsyncCallback;
import org.autumn.hsf.util.TLUtils;

/**
 * @author wanggf
 * @date 2013-8-31 下午3:13:42
 */
public class HsfSession {

    private IoSession session;

    private AtomicLong sequence = new AtomicLong(0);

    private ConcurrentHashMap<Long, InvocationFuture<Object>> futures = new ConcurrentHashMap<Long, InvocationFuture<Object>>();
    private ConcurrentHashMap<Long, AsyncCallback<Object>> callbacks = new ConcurrentHashMap<Long, AsyncCallback<Object>>();

    public HsfSession(IoSession session) {
        this.session = session;
    }

    public long getSeq() {
        return sequence.getAndIncrement();
    }

    /**
     * 同步请求
     * 
     * @param object
     * @return
     * @throws InterruptedException
     */
    public Object writeSync(RemoteServiceObject object) throws Throwable {
        final InvocationFuture<Object> future = new InvocationFuture<Object>();
        final Long seq = getSeq();
        object.setSeq(seq);
        futures.put(seq, future);
        session.write(object);
        return future.getResult();
    }

    /**
     * 异步请求,callback方式
     * 
     * @param object
     * @param callback
     */
    public void writeAsyncCallback(RemoteServiceObject object, AsyncCallback<Object> callback) {
        final Long seq = getSeq();
        callbacks.put(seq, callback);
        object.setSeq(seq);
        session.write(object);
    }

    /**
     * 异步请求，future方式
     * 
     * @param object
     */
    public void writeAsyncFuture(RemoteServiceObject object) {
        final Long seq = getSeq();
        final InvocationFuture<Object> future = new InvocationFuture<Object>();
        futures.put(seq, future);
        TLUtils.setData(InvocationFuture.class, future);
        object.setSeq(seq);
        session.write(object);
    }

    public InvocationFuture<Object> getInvocationFuture(Long seq) {
        return futures.get(seq);
    }

    public AsyncCallback<Object> getAsyncCallback(Long seq) {
        return callbacks.get(seq);
    }
}
