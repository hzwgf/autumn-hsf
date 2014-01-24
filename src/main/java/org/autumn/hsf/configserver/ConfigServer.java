/* 
 * @(#)ConfigServer.java    Created on 2013-9-3
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.configserver;

import java.io.IOException;

import org.autumn.hsf.HsfServer;

/**
 * @author wanggf
 * @date 2013-9-3 下午8:20:03
 */
public class ConfigServer extends HsfServer {

    public ConfigServer(int port) throws IOException {
        super(port);
    }

}
