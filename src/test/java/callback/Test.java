/* 
 * @(#)Test.java    Created on 2013-9-4
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package callback;

import org.autumn.hsf.HsfClient;
import org.autumn.hsf.mina.InvocationFuture;
import org.autumn.hsf.proxy.ServiceProxyFactory;
import org.autumn.hsf.service.TestService;
import org.autumn.hsf.util.AsyncCallback;
import org.autumn.hsf.util.AsyncFutureInvoker;

/**
 * @author wanggf
 * @date 2013-9-4 上午9:21:17
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        HsfClient client = new HsfClient("127.0.0.1", 8888, true);
        client.connect();
        testSync(client);
        testAsyncCallback(client, new MyCallback<Object>());
        testAsyncFuture(client);
    }

    private static void testSync(HsfClient hsfClient) {
        TestService service = ServiceProxyFactory.getFactoryInstance(hsfClient).wrapSyncProxy(TestService.class);
        Object result = null;
        result = service.test();
        System.out.println(result);
        result = service.test2();
        System.out.println(result);
        System.out.println(service.sum(1, "2"));
        try {
            service.test3();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testAsyncCallback(HsfClient hsfClient, AsyncCallback<Object> callback) {
        TestService service = ServiceProxyFactory.getFactoryInstance(hsfClient).wrapAsyncCallbackProxy(
                TestService.class, callback);
        Object result = null;
        result = service.test();
        System.out.println(result);
        result = service.test2();
        System.out.println(result);
        System.out.println(service.sum(1, "2"));
        try {
            service.test3();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testAsyncFuture(HsfClient hsfClient) {
        final TestService service = ServiceProxyFactory.getFactoryInstance(hsfClient).wrapAsyncFutureProxy(
                TestService.class);

        InvocationFuture<Object> future = new AsyncFutureInvoker<TestService>(TestService.class) {
            @Override
            public void invokeService() {
                try {
                    service.test3();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.invoke();
        try {
            System.out.println(future.getResult());
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static class MyCallback<T> extends AsyncCallback<T> {

        @Override
        public void doCallback(T data) {
            System.out.println(data);
        }
    }
}
