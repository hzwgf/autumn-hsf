/* 
 * @(#)Client.java    Created on 2013-9-4
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package callback;

import org.autumn.hsf.HsfClient;
import org.autumn.hsf.proxy.ServiceProxyFactory;
import org.autumn.hsf.service.TestService;

/**
 * @author wanggf
 * @date 2013-9-4 上午10:21:17
 */
public class Client {

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        HsfClient hsfClient = new HsfClient("127.0.0.1", 8082, true);
        hsfClient.connect();

        TestService service = ServiceProxyFactory.getFactoryInstance(hsfClient).wrapSyncProxy(TestService.class);
        System.out.println(service.test());
        System.out.println(service.test2());
        try {
            service.test3();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(service.sum(1, "s"));
    }

}
