/* 
 * @(#)HsfProtocolCodecFilter.java    Created on 2013-7-18
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.autumn.hsf.message.AbstractMessage;
import org.autumn.hsf.message.HeartBeat;
import org.autumn.hsf.message.resp.HeartBeatResp;
import org.autumn.hsf.util.StringUtils;

/**
 * @author wanggf
 * @date 2013-7-18 下午3:26:55
 */
public class HsfProtocolCodecFilter extends ProtocolCodecFilter {

    public HsfProtocolCodecFilter() {
        super(HsfProtocolEncoder.class, HsfProtocolDecoder.class);
    }

    /*
     * 协议编码类
     */
    public static class HsfProtocolEncoder extends ProtocolEncoderAdapter {

        public HsfProtocolEncoder() {

        }

        @Override
        public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
            IoBuffer buf = ((AbstractMessage) message).encode();
            out.write(buf);
        }
    }

    /*
     * 协议解码类
     */
    public static class HsfProtocolDecoder extends CumulativeProtocolDecoder {

        public HsfProtocolDecoder() {

        }

        /**
         * 解决粘包、少包的问题
         */
        @Override
        protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
            if (in.remaining() < 4) {
                return false;
            }
            else {
                int remain = in.remaining();
                int lenght = in.getInt();
                if (remain < lenght) {
                    return false;
                }
                byte[] bytes = new byte[32];
                in.get(bytes);
                String uuid = StringUtils.getStringByISO88591(bytes);
                bytes = new byte[lenght - 36];
                in.get(bytes);
                String content = StringUtils.getStringByUTF8(bytes);
                if ("在吗".equals(content)) {
                    out.write(new HeartBeat());
                }
                else if ("在".equals(content)) {
                    out.write(new HeartBeatResp());
                }
                else {

                }
                System.out.println(content);
                return (in.remaining() > 0);
            }
        }
    }

}
