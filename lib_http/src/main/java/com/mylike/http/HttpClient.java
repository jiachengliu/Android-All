package com.mylike.http;

/**
 * author: liuxiansheng
 * data: 2020-12-03 16:13
 * desc:处理配置，调用接口
 */
public class HttpClient {

    private Config config;
    public HttpClient(Builder builder){
        this.config = new Config(builder);
    }

    public Call newCall(Request request){
        return new HttpCall(config,request);
    }

    public static final class Config{
        final int connTimeout;
        final int readTimeout;
        final int writeTimeout;
        public Config(Builder builder){
            this.connTimeout = builder.connTimeout;
            this.readTimeout = builder.readTimeout;
            this.writeTimeout = builder.writeTimeout;
        }
    }
    public static final class Builder{
        private int connTimeout;
        private int readTimeout;
        private int writeTimeout;
        public Builder(){
            this.connTimeout = 10*1000;
            this.readTimeout = 10*1000;
            this.writeTimeout = 10*1000;
        }
        public Builder readTimeout(int readTimeout){
            this.readTimeout = readTimeout;
            return this;
        }
        public Builder writeTimeout(int writeTimeout){
            this.writeTimeout = writeTimeout;
            return this;
        }
        public Builder connTimeout(int connTimeout){
            this.connTimeout = connTimeout;
            return this;
        }
        public HttpClient build(){
            return new HttpClient(this);
        }
    }
}
