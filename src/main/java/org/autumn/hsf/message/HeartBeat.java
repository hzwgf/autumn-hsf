/* 
 * @(#)HeartBeat.java    Created on 2013-7-19
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.message;

import org.apache.mina.core.buffer.IoBuffer;
import org.autumn.hsf.util.StringUtils;

/**
 * @author wanggf
 * @date 2013-7-19 上午10:24:41
 */
public class HeartBeat extends AbstractMessage {

    private String content = "在吗";

    @Override
    protected byte[] getBytes() {
        return StringUtils.getBytesUTF8(content);
    }

    @Override
    protected void valueOf(IoBuffer buffer) {

    }

    @Override
    public String toString() {
        return content;
    }

}
