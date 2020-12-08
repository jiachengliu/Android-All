package com.mylike.http;

/**
 * author: liuxiansheng
 * data: 2020-12-03 15:59
 * desc:
 */
public class Response {
    public final ResponseBody body;
    public final String message;
    public final int code;
    public Response(Builder builder){
        this.body = builder.body;
        this.code = builder.code;
        this.message = builder.message;
    }
    public static final class Builder{
        private ResponseBody body;
        private String message;
        private int code;
        public Builder body(ResponseBody body){
            this.body = body;
            return this;
        }
        public Builder message(String message){
            this.message = message;
            return this;
        }
        public Builder code(int code){
            this.code = code;
            return this;
        }
        public Response build(){
            if (message==null)throw new NullPointerException("response messge == null");
            if (body == null) throw new NullPointerException("response body == null");
            return new Response(this);
        }
    }
}
