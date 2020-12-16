package com.mylike.java.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: liuxiansheng
 * data:2020/12/15 14:51
 * desc:循环等待锁
 * 形成了一个锁依赖的环路。以Thread1为例，它先将第一个对象锁住，但是当它试着向第二个对象获取锁时，它就会进入等待状态，
 *因为第二个对象已经被另一个线程锁住了。这样以此类推，Thread1依赖Thread2锁住的对象object2，Thread2依赖Thread3锁住的对象object3，
 *而Thread3依赖Thread1锁住的对象object1，从而导致了死锁。在线程引起死锁的过程中，就形成了一个依赖于资源的循环。
 */
public class SyncThread implements Runnable{

    private AtomicInteger object1;//不可以使用Integer,因其自动拆箱装箱特性
    private AtomicInteger object2;
    public SyncThread(AtomicInteger object1,AtomicInteger object2){
        this.object1 = object1;
        this.object2 = object2;
    }
//    @Override
//    public void run() {
//        String name = Thread.currentThread().getName();
//        System.out.println("当前线程："+name);
//        synchronized (object1){
//            System.out.println(name+"锁住了"+object1);
//            dealWork();
//            synchronized (object2){
//                System.out.println(name+"锁住了"+object2);
//                dealWork();
//            }
//            System.out.println(name+"释放了"+object2);
//        }
//        System.out.println(name+"释放了"+object1);
//        System.out.println(name+"结束");
//    }
    @Override
    public void run() {//避免锁的嵌套。
        String name = Thread.currentThread().getName();
        System.out.println("当前线程："+name);
        synchronized (object1){
            System.out.println(name+"锁住了"+object1);
            dealWork();
        }
        System.out.println(name+"释放了"+object1);
        synchronized (object2){
            System.out.println(name+"锁住了"+object2);
            dealWork();
        }
        System.out.println(name+"释放了"+object2);

        System.out.println(name+"结束");
    }

    private void dealWork() {
        try {
            System.out.println("开始处理dealwork");
            Thread.sleep(3000);
//            System.out.println("结束处理dealwork");
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
