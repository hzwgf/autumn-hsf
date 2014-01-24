/* 
 * @(#)HsfClientHandler.java    Created on 2013-7-18
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wanggf
 * @date 2013-7-18 下午4:26:07
 */
public class HsfHandler extends IoHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(HsfHandler.class);

    @Override
    public void sessionClosed(IoSession session) throws Exception {
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.info("messageReceived:" + message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        log.info("messageSent:" + message);
    }

}
