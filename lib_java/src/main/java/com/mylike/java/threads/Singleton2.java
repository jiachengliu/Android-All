package com.mylike.java.threads;

/**
 * author: liuxiansheng
 * data:2020/12/18 16:11
 * desc:直接使用synchronized锁住对象，只有在singleton为空再去锁，这样可以提高效率。
 * 但是不添加volatile，可能会导致创建实例的时候处理器重排序。
 * 原因是创建实例的时候，有几个步骤（分配空间，初始化，把instance执行分配的地址），
 * 这几个步骤会被处理器重排序，导致在没有完全初始化的情况下其他线程获取了实例，
 * 可能会导致莫名其妙的问题。使用volatile可以避免重排序。
 */
public class Singleton2 {

    private static volatile Singleton2 singleton2;

    public static Singleton2 getInstance(){
        if (singleton2==null){
            synchronized (singleton2){
                singleton2 = new Singleton2();
//                return singleton2;
            }
        }else {
//            return singleton2;
        }
        return singleton2;
    }
}
