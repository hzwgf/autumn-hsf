/* 
 * @(#)CallbackType.java    Created on 2013-9-1
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.util;

/**
 * @author wanggf
 * @date 2013-9-1 下午4:14:26
 */
public enum CallbackType {

    SYNC(0), CALLBACK(1), FUTURE(2), UNKNOW(-1);

    private int value = 0;

    private CallbackType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
