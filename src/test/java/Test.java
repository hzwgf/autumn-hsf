/* 
 * @(#)Test.java    Created on 2013-7-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */

/**
 * @author wanggf
 * @date 2013-7-30 下午7:59:49
 */
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

    public static void t2() {
        new Thread("first") {
            @Override
            public void run() {
                try {

                    synchronized (new String("1").intern()) {
                        wait();
                    }
                }
                catch (Exception e) {

                }
                System.out.println("fisrt over");
            }
        }.start();

        new Thread("second") {

            @Override
            public void run() {
                synchronized (new String("1").intern()) {

                }

            }

        }.start();

    }

    public static void t1() {

        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        try {
                            throw new Exception();
                        }
                        finally {
                            System.out.println("finally");
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public static void t() {

        Thread t = new Thread() {

            @Override
            public void run() {

                // try {
                while (true) {
                    // Thread.currentThread().sleep(1000);
                    System.out.print(1);
                }
                // }
                // catch (InterruptedException e) {
                //
                // }

            }
        };
        t.start();

        try {
            Thread.currentThread().sleep(2000L);
            t.interrupt();
            System.out.println(t.isAlive());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
