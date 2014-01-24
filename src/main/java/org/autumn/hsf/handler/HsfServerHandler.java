/* 
 * @(#)HsfServerHandler.java    Created on 2013-7-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.autumn.hsf.HsfServer;
import org.autumn.hsf.pojo.InvocationResult;
import org.autumn.hsf.pojo.RemoteServiceObject;
import org.autumn.hsf.util.EndlessLoopUtils;
import org.autumn.hsf.util.ReflectionUtils;

/**
 * @author wanggf
 * @date 2013-7-24 下午5:01:39
 */
public class HsfServerHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) {
        if (message instanceof RemoteServiceObject) {
            InvocationResult r = null;
            RemoteServiceObject rso = (RemoteServiceObject) message;
            r = new InvocationResult();
            r.setSeq(rso.getSeq());
            try {
                Object result = ReflectionUtils.invoke(HsfServer.findService(rso.getService()), rso.getMethod(),
                        rso.getArgs());
                r.setResult(result);
            }
            catch (Exception e) {
                EndlessLoopUtils.fixEndlessLoop(e);
                r.setResult(e);
            }
            session.write(r);
        }
    }
}
