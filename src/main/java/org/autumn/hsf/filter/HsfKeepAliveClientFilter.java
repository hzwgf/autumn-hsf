/* 
 * @(#)HsfKeepAliveFilter.java    Created on 2013-7-18
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.filter;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.autumn.hsf.message.HeartBeat;
import org.autumn.hsf.message.resp.HeartBeatResp;

/**
 * @author wanggf
 * @date 2013-7-18 下午4:00:14
 */
public class HsfKeepAliveClientFilter extends KeepAliveFilter {

    public HsfKeepAliveClientFilter() {
        super(new KeepAliveMessageFactory() {

            @Override
            public boolean isRequest(IoSession session, Object message) {
                return message instanceof HeartBeat;
            }

            @Override
            public boolean isResponse(IoSession session, Object message) {
                return message instanceof HeartBeatResp;
            }

            /**
             * 这个filter添加在客户端，可以实现只是服务器问客户端在不在，而客户端只是应答
             */
            @Override
            public Object getRequest(IoSession session) {
                return null;
            }

            @Override
            public Object getResponse(IoSession session, Object request) {
                return new HeartBeatResp();
            }

        }, IdleStatus.BOTH_IDLE);

    }
}
