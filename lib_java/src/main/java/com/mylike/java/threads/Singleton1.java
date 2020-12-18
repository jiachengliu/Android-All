package com.mylike.java.threads;

/**
 * author: liuxiansheng
 * data:2020/12/18 16:06
 * desc:全局单例模式，如果不添加synchronized，不能保证在多线程中确保安全，
 * 但是使用synchronized，每次锁住对象，导致性能降低。
 */
public class Singleton1 {
    private static Singleton1 singleton1;

    public static synchronized Singleton1 getInstance(){
        if (singleton1 == null){
            return new Singleton1();
        }
        return singleton1;
    }
    private Singleton1(){

    }
}
