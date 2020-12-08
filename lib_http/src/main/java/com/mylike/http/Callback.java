package com.mylike.http;

import java.io.IOException;

/**
 * author: liuxiansheng
 * data: 2020-12-03 16:09
 * desc:
 */
public interface Callback {
    /**
     * 成功时候回调
     * @param response
     */
    void onResponse(Response response);

    /**
     * 失败时候回调
     * @param request
     * @param e
     */
    void onFail(Request request, IOException e);
}
