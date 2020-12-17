package com.mylike.java.threads;

import java.util.concurrent.Callable;

/**
 * author: liuxiansheng
 * data:2020/12/17 16:08
 * desc:
 */
public class MyCallable {
    public static void main(String[] args) {

    }
}
class InnerCallable<T> implements Callable<T>{

    @Override
    public T call() throws Exception {
        return null;
    }
}
