/* 
 * @(#)HsfServerHandler.java    Created on 2013-7-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.autumn.hsf.HsfClient;
import org.autumn.hsf.mina.InvocationFuture;
import org.autumn.hsf.pojo.InvocationResult;
import org.autumn.hsf.util.AsyncCallback;

/**
 * @author wanggf
 * @date 2013-7-24 下午5:01:39
 */
public class HsfClientHandler extends IoHandlerAdapter {

    private HsfClient hsfClient;

    public HsfClientHandler(HsfClient hsfClient) {
        this.hsfClient = hsfClient;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        if (message instanceof InvocationResult) {
            InvocationResult r = (InvocationResult) message;

            InvocationFuture<Object> f = hsfClient.getSession().getInvocationFuture(r.getSeq());
            if (f != null) {
                if (r.getResult() instanceof Throwable) {
                    f.setCause((Throwable) r.getResult());
                }
                else {
                    f.setResult(r.getResult());
                }
            }

            AsyncCallback<Object> callback = hsfClient.getSession().getAsyncCallback(r.getSeq());
            if (callback != null) {
                if (r.getResult() instanceof Throwable) {
                    callback.doExceptionCaught((Throwable) r.getResult(), session);
                }
                else {
                    callback.doCallback(r.getResult());
                }
            }
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        if (hsfClient.isReconnectWhenConnectFail()) {
            new Thread() {
                @Override
                public void run() {

                    while (true) {
                        try {
                            Thread.sleep(3000);
                            if (hsfClient.connect()) {
                                break;
                            }
                        }
                        catch (InterruptedException e) {
                            // ignore
                        }
                    }
                }
            }.start();
        }
    }
}
