import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* 
 * @(#)TestServiceImple.java    Created on 2013-7-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */

/**
 * @author wanggf
 * @date 2013-7-24 下午2:25:29
 */
public class TestServiceImple implements TestService {

    @Override
    public String test() {
        return "hello world";
    }

    @Override
    public String test2() {
        return "hello world2";
    }

    @Override
    public int sum(int first, int second) {
        return first + second;
    }

    public static void main(String[] args) {

        // TestService service = ((TestService) Proxy.newProxyInstance(TestServiceImple.class.getClassLoader(),
        // TestServiceImple.class.getInterfaces(), new MyInvocationHandler(new TestServiceImple())));
        // System.out.println(service.test());
        // System.out.println(service.test2());
        // System.out.println(service.sum(1, 2));

        TestServiceImple service = new TestServiceImple();
        try {
            Method m = service.getClass().getMethod("test2");
            try {
                Object obj = m.invoke(service);
                System.out.println(obj);
            }
            catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
