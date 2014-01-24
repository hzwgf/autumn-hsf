/* 
 * @(#)TLUtils.java    Created on 2013-9-1
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.util;

import java.util.HashMap;

/**
 * @author wanggf
 * @date 2013-9-1 下午4:59:26
 */
public class TLUtils {

    private static ThreadLocal<HashMap<Object, Object>> threadLocal = new ThreadLocal<HashMap<Object, Object>>() {

        @Override
        protected HashMap<Object, Object> initialValue() {
            return new HashMap<Object, Object>();
        }

    };

    public static void setData(Object key, Object value) {
        getMap().put(key, value);
    }

    public static Object getData(Object key) {
        return getMap().get(key);
    }

    private static HashMap<Object, Object> getMap() {
        return threadLocal.get();
    }
}
