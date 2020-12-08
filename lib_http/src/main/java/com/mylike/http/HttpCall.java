package com.mylike.http;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * author: liuxiansheng
 * data: 2020-12-04 09:38
 * desc:
 */
public class HttpCall implements Call{
    final Request request;
    final HttpClient.Config config;
    private IRequestHandler requestHandler = new RequestHandler();
    public HttpCall(HttpClient.Config config,Request request){
        this.request = request;
        this.config = config;
    }
    @Override
    public Response execute() {
        Callable<Response> task = new SyncTask();
        Response response;
        try {
            response = HttpThreadPool.getInstance().submit(task);
            return response;
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return new Response.Builder()
                .code(400)
                .message("线程异常中断")
                .body(new ResponseBody(null))
                .build();
    }

    @Override
    public void enqueue(Callback callback) {

        Runnable runnable = new HttpTask(this,callback,requestHandler);
        HttpThreadPool.getInstance().execute(new FutureTask<>(runnable,null));
    }
    class SyncTask implements Callable<Response>{
        @Override
        public Response call() throws Exception {
            Response response = requestHandler.handlerRequest(HttpCall.this);
            return response;
        }
    }
}
