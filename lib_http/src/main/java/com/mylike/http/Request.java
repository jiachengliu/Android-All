package com.mylike.http;

import android.text.TextUtils;
import android.util.ArrayMap;

import java.util.Map;

/**
 * author: liuxiansheng
 * data: 2020-12-03 14:57
 * desc:
 */
public class Request {
    final HttpMethed methed;
    final String url;
    final RequestBody body;
    final Map<String ,String> heads;
    public Request(Builder builder){
        this.methed = builder.methed;
        this.url = builder.url;
        this.heads = builder.heads;
        this.body = builder.body;
    }

    public static final class Builder{
        HttpMethed methed;
        String url;
        Map<String ,String> heads;
        RequestBody body;
        public Builder(){
            this.methed = HttpMethed.GET;
            this.heads = new ArrayMap<>();
        }
        Builder(Request request){
            this.methed = request.methed;
            this.url = request.url;
        }
        public Builder url(String url){
            this.url = url;
            return this;
        }
        public Builder header(String name,String value){
            Util.checkMap(name,value);
            heads.put(name,value);
            return this;
        }
        public Builder get(){
            methed(HttpMethed.GET,null);
            return this;
        }
        public Builder methed(HttpMethed methed,RequestBody body){
            this.methed = methed;
            this.body = body;
            return this;
        }
        public Request build(){
            if (url == null) {
                throw new IllegalStateException("访问url不能为空");
            }
            if (body!=null){
                if (!TextUtils.isEmpty(body.contentType())){
                    heads.put("Content-Type",body.contentType());
                }
            }
            heads.put("Connection","Keep-Alive");
            heads.put("Charset","UTF-8");
            return new Request(this);
        }

    }

    public enum HttpMethed{
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");
        public String methodValue = "";
        HttpMethed(String methodValue){
            this.methodValue = methodValue;
        }
        public static boolean checkNeedBody(HttpMethed methed){
            return POST.equals(methed)||PUT.equals(methed);
        }
        public static boolean checkNoBody(HttpMethed methed){
            return GET.equals(methed)||DELETE.equals(methed);
        }
    }
}
