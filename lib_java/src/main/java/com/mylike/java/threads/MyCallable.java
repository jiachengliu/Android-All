package com.mylike.java.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * author: liuxiansheng
 * data:2020/12/17 16:08
 * desc:创建类InnerCallable 实现Callable接口，
 * 创建FutureTask对象，传入callable，
 * 把futureTask传入Thread，然后start。
 * 注：public class FutureTask<V> implements RunnableFuture<V>
 *    public interface RunnableFuture<V> extends Runnable, Future<V>
 *
 */
public class MyCallable {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        for (int i =0;i<30;i++) {
            Callable<String> callable = new InnerCallable(i);
            FutureTask<String> futureTask = new FutureTask<>(callable);
            Thread thread = new Thread(futureTask,"thread-"+i);//FutureTask<>-》RunnableFuture<V>-》extends Runnable, Future<V>
            thread.start();
            try {
                System.out.println("线程：" + thread.getName() + ",获取的callback的值：" + futureTask.get(1, TimeUnit.SECONDS));
            }catch (TimeoutException e){

            }
            //futureTask.get()：当任务结束后返回一个结果，如果调用时，工作还没有结束，则会阻塞线程，直到任务执行完毕；
            //get（long timeout,TimeUnit unit）做最多等待timeout的时间就会返回结果,等待的时间内没有返回结果，报异常TimeoutException。
            //cancel（boolean mayInterruptIfRunning）方法可以用来停止一个任务，如果任务可以停止（通过mayInterruptIfRunning来进行判断），
            // 则可以返回true,如果任务已经完成或者已经停止，或者这个任务无法停止，则会返回false.
            //isDone（）方法判断当前方法是否完成
            //isCancel（）方法判断当前方法是否取消
        }
        System.out.println("执行结束");
    }
}
class InnerCallable<T> implements Callable<T>{
    private int i;
    public InnerCallable (int i){
        this.i = i;
    }
    @Override
    public T call() throws Exception {
        String threadName = Thread.currentThread().getName();
        T t = (T)(threadName+","+i);
        if (i==10){
            Thread.sleep(5000);
            return (T)(threadName+"特殊10");
        }
//        System.out.println("准备return");
        return t;
    }
}
//        线程：thread-0,获取的callback的值：thread-0,0
//        线程：thread-1,获取的callback的值：thread-1,1
//        线程：thread-2,获取的callback的值：thread-2,2
//        线程：thread-3,获取的callback的值：thread-3,3
//        线程：thread-4,获取的callback的值：thread-4,4
//        线程：thread-5,获取的callback的值：thread-5,5
//        线程：thread-6,获取的callback的值：thread-6,6
//        线程：thread-7,获取的callback的值：thread-7,7
//        线程：thread-8,获取的callback的值：thread-8,8
//        线程：thread-9,获取的callback的值：thread-9,9
//        线程：thread-11,获取的callback的值：thread-11,11
//        线程：thread-12,获取的callback的值：thread-12,12
//        线程：thread-13,获取的callback的值：thread-13,13
//        线程：thread-14,获取的callback的值：thread-14,14
//        线程：thread-15,获取的callback的值：thread-15,15
//        线程：thread-16,获取的callback的值：thread-16,16
//        线程：thread-17,获取的callback的值：thread-17,17
//        线程：thread-18,获取的callback的值：thread-18,18
//        线程：thread-19,获取的callback的值：thread-19,19
//        线程：thread-20,获取的callback的值：thread-20,20
//        线程：thread-21,获取的callback的值：thread-21,21
//        线程：thread-22,获取的callback的值：thread-22,22
//        线程：thread-23,获取的callback的值：thread-23,23
//        线程：thread-24,获取的callback的值：thread-24,24
//        线程：thread-25,获取的callback的值：thread-25,25
//        线程：thread-26,获取的callback的值：thread-26,26
//        线程：thread-27,获取的callback的值：thread-27,27
//        线程：thread-28,获取的callback的值：thread-28,28
//        线程：thread-29,获取的callback的值：thread-29,29
//        执行结束
