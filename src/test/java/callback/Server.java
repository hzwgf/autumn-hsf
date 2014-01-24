/* 
 * @(#)Server.java    Created on 2013-9-4
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package callback;

import java.io.IOException;

import org.autumn.hsf.HsfServer;
import org.autumn.hsf.service.TestServiceImpl;

/**
 * @author wanggf
 * @date 2013-9-4 上午10:20:25
 */
public class Server {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        HsfServer server = new HsfServer(8082);
        server.registerService(new TestServiceImpl());
    }
}
