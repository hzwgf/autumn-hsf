/* 
 * @(#)InvocationResult.java    Created on 2013-7-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.pojo;

import java.io.Serializable;

/**
 * @author wanggf
 * @date 2013-7-24 下午9:05:52
 */
public class InvocationResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private Object result;
    private Long seq;

    public InvocationResult() {
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

}
