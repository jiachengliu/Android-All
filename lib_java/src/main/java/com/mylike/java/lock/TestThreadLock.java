package com.mylike.java.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadLock {

    public static void main(String[] args) throws InterruptedException{
        //DeadLock 测试代码
//        Thread thread1 = new Thread(new DeadLock(true),"线程1");
//        Thread thread2 = new Thread(new DeadLock(false),"线程2");
//        thread1.start();
//        thread2.start();

//        Thread thread11 = new Thread(new DeadLock(false),"线程1");
//        Thread thread22 = new Thread(new DeadLock(false),"线程2");
//        thread11.start();
//        thread22.start();

        //SyncThread 测试代码
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();
        AtomicInteger int1 = new AtomicInteger(1);
        AtomicInteger int2 = new AtomicInteger(2);
        AtomicInteger int3 = new AtomicInteger(3);
        Thread thread1 = new Thread(new SyncThread(int1,int2),"thread1");
        Thread thread2 = new Thread(new SyncThread(int2,int3),"thread2");
        Thread thread3 = new Thread(new SyncThread(int3,int1),"thread3");
        thread1.start();
        Thread.sleep(1000);
        thread2.start();
        Thread.sleep(1000);
        thread3.start();

    }

}
