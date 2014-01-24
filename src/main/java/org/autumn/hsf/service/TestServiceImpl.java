package org.autumn.hsf.service;

import org.autumn.hsf.util.ReflectionUtils;

/* 
 * @(#)TestServiceImple.java    Created on 2013-7-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */

/**
 * @author wanggf
 * @date 2013-7-24 下午2:25:29
 */
public class TestServiceImpl implements TestService {

    @Override
    public String test() {
        return "hello world";
    }

    @Override
    public String test2() {
        return "hello world2";
    }

    @Override
    public void test3() throws Exception {
        throw new Exception("i不能等于2");
    }

    @Override
    public String sum(Integer first, String second) {
        return first + second;
    }

    public static void main(String[] args) {
        TestServiceImpl s = new TestServiceImpl();
        Object result = null;
        try {
            result = ReflectionUtils.invoke(s, "test3", 1);
            System.out.println(result);
            // result = ReflectionUtils.invoke(s, "test3", 3);
            // System.out.println(result);
            result = ReflectionUtils.invoke(s, "test3", 2);
            System.out.println(result);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
