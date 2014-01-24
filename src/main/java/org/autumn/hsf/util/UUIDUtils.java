/* 
 * @(#)UUIDUtils.java    Created on 2013-7-19
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.util;

import java.util.UUID;

/**
 * @author wanggf
 * @date 2013-7-19 下午1:58:13
 */
public class UUIDUtils {

    public static String random() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
