package com.mylike.http;

import java.io.IOException;

/**
 * author: liuxiansheng
 * data: 2020-12-03 16:57
 * desc:
 */
public interface IResponseHandler {
    void handlerSuccess(Callback callback,Response response);
    void handFail(Callback callback, Request request, IOException e);

    IResponseHandler RESPONSE_HANDLER = new IResponseHandler() {
        @Override
        public void handlerSuccess(final Callback callback, final Response response) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(response);
                }
            };
            execute(runnable);
        }

        @Override
        public void handFail(final Callback callback, final Request request, final IOException e) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    callback.onFail(request,e);
                }
            };
            execute(runnable);
        }
        public void removeAllMessage(){

        }
        private void execute(Runnable runnable){

        }
    };

}
