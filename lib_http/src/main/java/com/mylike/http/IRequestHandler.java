package com.mylike.http;

import java.io.IOException;

/**
 * author: liuxiansheng
 * data: 2020-12-03 16:36
 * desc:
 */
public interface IRequestHandler {
    /**
     * 处理发起请求
     * @param call
     * @return
     * @throws IOException
     */
    Response handlerRequest(HttpCall call)throws IOException;
}
