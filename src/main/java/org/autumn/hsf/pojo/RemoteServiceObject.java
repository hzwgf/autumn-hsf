/* 
 * @(#)RemoteServiceObject.java    Created on 2013-7-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.pojo;

import java.io.Serializable;

/**
 * @author wanggf
 * @date 2013-7-24 下午5:08:49
 */
public class RemoteServiceObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long seq;
    private String service;
    private String method;
    private Object[] args;

    public RemoteServiceObject() {

    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

}
