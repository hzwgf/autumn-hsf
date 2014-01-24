/* 
 * @(#)InvocationFuture.java    Created on 2013-8-31
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package org.autumn.hsf.mina;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wanggf
 * @date 2013-8-31 下午3:41:28
 */
public class InvocationFuture<V> {

    private V result;
    private AtomicBoolean done = new AtomicBoolean(false);
    private AtomicBoolean success = new AtomicBoolean(false);
    private Semaphore semaphore = new Semaphore(0);
    private Throwable cause;

    public InvocationFuture() {
    }

    public V getResult() throws Throwable {
        semaphore.acquire();
        if (!success.get() && cause != null) {
            throw cause;
        }
        return this.result;
    }

    public V getResult(long timeout, TimeUnit unit) throws Throwable {
        if (semaphore.tryAcquire(timeout, unit)) {
            if (!success.get() && cause != null) {
                throw cause;
            }
            return this.result;
        }
        else {
            throw new TimeoutException("等待结果超时");
        }
    }

    public Throwable getCause() {
        return cause;
    }

    public void setResult(V result) {
        this.result = result;
        done.set(true);
        success.set(true);
        semaphore.release(Integer.MAX_VALUE - semaphore.availablePermits());
    }

    public void setCause(Throwable t) {
        this.cause = t;
        done.set(true);
        success.set(false);
        semaphore.release(Integer.MAX_VALUE - semaphore.availablePermits());
    }

}
