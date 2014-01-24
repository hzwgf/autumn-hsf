package org.autumn.hsf.service;

import org.autumn.hsf.annotation.RemoteServiceContract;

/* 
 * @(#)TestService.java    Created on 2013-7-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */

/**
 * @author wanggf
 * @date 2013-7-24 下午2:24:44
 */
@RemoteServiceContract
public interface TestService {

    String test();

    String test2();

    void test3() throws Exception;

    String sum(Integer first, String second);

}
