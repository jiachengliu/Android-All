package com.mylike.java.lock;

/**
 * 一个多线程死锁的经典实例，对应执行类 TestThreadLock
 */
public class DeadLock implements Runnable{

    private static Object object1 = new Object();
    private static Object object2 = new Object();
    private boolean flag;
    public DeadLock(boolean flag){
        this.flag = flag;
    }
    @Override
    public void run() {
        System.out.println("运行的线程："+Thread.currentThread().getName());
        if (flag){
            synchronized (object1){
                System.out.println(Thread.currentThread().getName()+" 锁住了 "+object1);
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("运行的线程：object1结束");
                synchronized(object2){// 执行不到这里
                    System.out.println("1秒钟后，"+Thread.currentThread().getName()+" 锁住了 "+object2);
                }
            }
            System.out.println("运行的线程：object1");
        }else {
            synchronized (object2){
                System.out.println(Thread.currentThread().getName()+" 锁住了 "+object2);
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("运行的线程：object2结束");
                synchronized (object1){//执行不到这里
                    System.out.println("1秒钟后，"+Thread.currentThread().getName()+" 锁住了"+object1);
                }
            }
            System.out.println("运行的线程：object2");
        }
    }
}
