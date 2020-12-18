package com.mylike.java.threads;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author: liuxiansheng
 * data:2020/12/18 10:27
 * desc:简单线程池实例，
 * 解决：1.频繁的创建和销毁线程会给服务器带来很大的压力
 * 2.若创建的线程不销毁而是留在线程池中等待下次使用，则会很大地提高效率也减轻了服务器的压力
 */
public class MyThreadPool {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ThreadPool threadPool = new ThreadPool();
        for (int i=0;i<10;i++){
            executorService.submit(threadPool);
        }
        executorService.shutdown();
//        for (int i=0;i<50;i++) {
//            new Thread(threadPool, "thread=" + i).start();
//        }
    }
}

class ThreadPool implements Runnable{//Atomic： 原子的
//需要注意的是，AtomicInteger和Integer，int之前的区别，AtomicInteger可以保证线程安全。
//    涉及到拆箱装箱知识。
    private AtomicInteger i=new AtomicInteger(0);//可保证多线程中保持原子操作
//    private Integer i;
//    private int i;
    @Override
    public void run() {
        synchronized (i) {
            i.getAndAdd(1);
            System.out.println("thread:" + Thread.currentThread().getName() + ",i:" + i);
        }
    }
}
//        thread:pool-1-thread-1,i:1
//        thread:pool-1-thread-5,i:2
//        thread:pool-1-thread-5,i:3
//        thread:pool-1-thread-4,i:4
//        thread:pool-1-thread-3,i:5
//        thread:pool-1-thread-3,i:6
//        thread:pool-1-thread-2,i:7
//        thread:pool-1-thread-4,i:8
//        thread:pool-1-thread-5,i:9
//        thread:pool-1-thread-1,i:10