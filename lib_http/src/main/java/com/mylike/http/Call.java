package com.mylike.http;

/**
 * author: liuxiansheng
 * data: 2020-12-03 16:08
 * desc:
 */
public interface Call {
    /**
     * 同步
     * @return
     */
    Response execute();

    /**
     * 异步-需要回调
     */
    void enqueue(Callback callback);
}
