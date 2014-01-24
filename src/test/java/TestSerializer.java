import java.util.HashMap;

import org.autumn.hsf.pojo.RemoteServiceObject;
import org.autumn.hsf.serializer.KryoSerializer;

/* 
 * @(#)TestSerializer.java    Created on 2013-7-25
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */

/**
 * @author wanggf
 * @date 2013-7-25 下午6:13:27
 */
public class TestSerializer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        KryoSerializer kryo = new KryoSerializer();
        RemoteServiceObject rso = new RemoteServiceObject();
        rso.setService("TestService");
        rso.setMethod("test");
        byte[] bytes = kryo.serialize(rso);

        Object obj = null;
        try {
            obj = kryo.deserialize(bytes);
            RemoteServiceObject result = (RemoteServiceObject) obj;
            System.out.println(result.getService());
            System.out.println(result.getMethod());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println((obj instanceof HashMap));

    }
}
