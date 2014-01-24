/* 
 * @(#)StringUtils.java    Created on 2013-7-19
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.util;

import java.io.UnsupportedEncodingException;

/**
 * @author wanggf
 * @date 2013-7-19 上午11:29:54
 */
public class StringUtils {

    public static byte[] getBytes(String s, String charset) {
        try {
            return s.getBytes(charset);
        }
        catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public static byte[] getBytesISO88591(String s) {
        return getBytes(s, "ISO-8859-1");
    }

    public static byte[] getBytesUTF8(String s) {
        return getBytes(s, "UTF-8");
    }

    public static String getStringByISO88591(byte[] bytes) {
        try {
            return new String(bytes, "ISO-8859-1");
        }
        catch (UnsupportedEncodingException e) {
            //
        }
        return null;
    }

    public static String getStringByUTF8(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            //
        }
        return null;
    }

}
