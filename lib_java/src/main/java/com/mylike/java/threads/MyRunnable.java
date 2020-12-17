package com.mylike.java.threads;

/**
 * author: liuxiansheng
 * data:2020/12/17 15:54
 * desc:
 */
public class MyRunnable {
    public static void main(String[] args) {
        for (int i=0;i<10;i++) {
            new Thread(new InnerRunnable(i),"thread-"+i).start();
        }
        System.out.println("当前线程名称："+Thread.currentThread().getName());
    }
}
class InnerRunnable implements Runnable{
    int i;
    public InnerRunnable(int i){
        this.i = i;
    }
    @Override
    public void run() {
        System.out.println(i+",当前线程名称："+Thread.currentThread().getName());
    }
}
//0,当前线程名称：thread-0
//当前线程名称：main
//1,当前线程名称：thread-1
//5,当前线程名称：thread-5
//6,当前线程名称：thread-6
//2,当前线程名称：thread-2
//3,当前线程名称：thread-3
//4,当前线程名称：thread-4
//7,当前线程名称：thread-7
//8,当前线程名称：thread-8
//9,当前线程名称：thread-9
