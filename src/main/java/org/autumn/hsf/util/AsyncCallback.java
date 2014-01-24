package org.autumn.hsf.util;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: AsyncCallback.java
 * @Package org.summercool.hsf.proxy
 * @Description: 异步处理回调
 * @author 简道
 * @date 2011-9-17 下午2:56:13
 * @version V1.0
 */
public abstract class AsyncCallback<T> {

    private static final Logger logger = LoggerFactory.getLogger(AsyncCallback.class);

    /**
     * @Title: doCallback
     * @Description: 回调处理消息
     * @author 简道
     * @param data
     *            消息
     * @return void 返回类型
     */
    public abstract void doCallback(T data);

    /**
     * 
     * @param e
     */
    public void doExceptionCaught(Throwable e, IoSession session) {
        logger.error("send msg to channel({}) occurs exception:{}", new Object[] { session.getRemoteAddress(), e });
    }
}
