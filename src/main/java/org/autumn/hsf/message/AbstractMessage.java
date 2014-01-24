/* 
 * @(#)AbstractMessage.java    Created on 2013-7-19
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.message;

import org.apache.mina.core.buffer.IoBuffer;
import org.autumn.hsf.util.StringUtils;
import org.autumn.hsf.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wanggf
 * @date 2013-7-19 上午10:31:27
 */
public abstract class AbstractMessage {

    private static final Logger log = LoggerFactory.getLogger(AbstractMessage.class);

    /**
     * 消息报头字节长度
     */
    private final int HEADER_BYTE_LENGTH = 36;

    /**
     * 消息id字节长度，用uuid表示
     */
    private final int ID_BYTE_LENGTH = 32;

    /**
     * 消息长度
     */
    private int length;

    /**
     * 消息id
     */
    private String uuid;

    public final IoBuffer encode() {
        byte[] body = getBytes();
        length = HEADER_BYTE_LENGTH + (body == null ? 0 : body.length);
        IoBuffer buf = IoBuffer.allocate(length);
        buf.putInt(length);
        buf.put(StringUtils.getBytesISO88591(UUIDUtils.random()));
        buf.put(body);
        buf.flip();
        return buf;
    }

    public final void decode(IoBuffer buf) {
        length = buf.getInt();
        byte[] bytes = new byte[ID_BYTE_LENGTH];
        buf.get(bytes);
        uuid = UUIDUtils.random();
        valueOf(buf);
    }

    protected abstract byte[] getBytes();

    protected abstract void valueOf(IoBuffer buffer);
}
