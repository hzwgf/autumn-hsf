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
import org.autumn.hsf.serializer.KryoSerializer;

/**
 * @author wanggf
 * @date 2013-7-18 下午3:26:55
 */
public class HsfSerializerFilter extends ProtocolCodecFilter {

    private static final KryoSerializer kryo = new KryoSerializer();

    public HsfSerializerFilter() {
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
            IoBuffer buf = IoBuffer.allocate(10);
            buf.setAutoExpand(true);
            byte[] body = kryo.serialize(message);
            buf.putInt(body.length);
            buf.put(body);
            buf.flip();
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
                int length = in.getInt();
                int remain = in.remaining();
                if (remain < length) {
                    return false;
                }
                byte[] body = new byte[length];
                in.get(body);
                Object message = kryo.deserialize(body);
                out.write(message);
                return (in.remaining() > 0);
            }
        }
    }

}
