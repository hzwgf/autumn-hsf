import org.apache.mina.core.buffer.IoBuffer;
import org.autumn.hsf.util.StringUtils;
import org.autumn.hsf.util.UUIDUtils;

/* 
 * @(#)IoBufferTest.java    Created on 2013-7-20
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */

/**
 * @author wanggf
 * @date 2013-7-20 上午11:53:32
 */
public class IoBufferTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        IoBuffer buf = IoBuffer.allocate(10);
        buf.setAutoExpand(true);
        buf.putInt(42);
        buf.put(StringUtils.getBytesISO88591(UUIDUtils.random()));
        buf.put(StringUtils.getBytesUTF8("在吗"));
        buf.flip();

        int r1 = buf.remaining();
        buf.getInt();
        int r2 = buf.remaining();
    }
}
