package com.mylike.java.threads;

/**
 * author: liuxiansheng
 * data:2020/12/17 15:04
 * desc:继承Thread类，重写run方法（其实Thread类本身也实现了Runnable接口）
 * public class Thread implements Runnable
 */
public class MyThread {

    public static void main(String[] args) {
        for (int i=0;i<30;i++) {
            new InnerThread(i).start();
        }
        System.out.println("当前线程名称："+Thread.currentThread().getName());
    }

}
class InnerThread extends Thread{
    int i;
    public InnerThread(int i){
        this.i = i;
    }
    @Override
    public void run() {
        System.out.println(i+",当前线程名称："+Thread.currentThread().getName());
    }
}
//0,当前线程名称：Thread-0
//3,当前线程名称：Thread-3
//7,当前线程名称：Thread-7
//4,当前线程名称：Thread-4
//1,当前线程名称：Thread-1
//5,当前线程名称：Thread-5
//6,当前线程名称：Thread-6
//当前线程名称：main
//2,当前线程名称：Thread-2
//8,当前线程名称：Thread-8
//9,当前线程名称：Thread-9
//10,当前线程名称：Thread-10
//12,当前线程名称：Thread-12
//13,当前线程名称：Thread-13
//14,当前线程名称：Thread-14
//18,当前线程名称：Thread-18
//19,当前线程名称：Thread-19
//20,当前线程名称：Thread-20
//15,当前线程名称：Thread-15
//25,当前线程名称：Thread-25
//16,当前线程名称：Thread-16
//17,当前线程名称：Thread-17
//21,当前线程名称：Thread-21
//22,当前线程名称：Thread-22
//27,当前线程名称：Thread-27
//29,当前线程名称：Thread-29
//11,当前线程名称：Thread-11
//24,当前线程名称：Thread-24
//23,当前线程名称：Thread-23
//28,当前线程名称：Thread-28
//26,当前线程名称：Thread-26