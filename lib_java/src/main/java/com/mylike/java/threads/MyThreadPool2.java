package com.mylike.java.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * author: liuxiansheng
 * data:2020/12/18 14:34
 * desc:可以保证线程安全，同步执行。
 */
public class MyThreadPool2 {
    public static void main(String[] args) throws ExecutionException,InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        InnerCallbale1<Integer> innerCallable;
        for (int i=0;i<10;i++){
            innerCallable = new InnerCallbale1(i);
            Future<Integer> future = executorService.submit(innerCallable);
            System.out.println("返回值："+future.get());
        }
        executorService.shutdown();
    }
}
    class InnerCallbale1<T> implements Callable<T>{

        int i=0;
        public InnerCallbale1(int i){
            this.i = i;
        }
        @Override
        public T call() throws Exception {
            i++;
            return (T)(i+"");
        }
    }
//        返回值：1
//        返回值：2
//        返回值：3
//        返回值：4
//        返回值：5
//        返回值：6
//        返回值：7
//        返回值：8
//        返回值：9
//        返回值：10