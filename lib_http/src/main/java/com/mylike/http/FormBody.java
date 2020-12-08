package com.mylike.http;

import android.text.TextUtils;
import android.util.ArrayMap;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * author: liuxiansheng
 * data: 2020-12-04 16:05
 * desc:
 */
public class FormBody extends RequestBody{

    public static final int MAX_FORM = 1000;
    final Map<String, String> map;
    public FormBody(Builder builder){
        this.map = builder.map;
    }


    @Override
    String contentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    @Override
    void wirteTo(OutputStream ops) throws IOException {
        ops.write(transToString(map).getBytes("UTF-8"));
    }
    private String transToString(Map<String ,String> map) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        Set<String> keys = map.keySet();
        for (String key: keys){
            if (!TextUtils.isEmpty(stringBuffer)){
                stringBuffer.append("&");
            }
            stringBuffer.append(URLEncoder.encode(key,"UTF-8"));
            stringBuffer.append("=");
            stringBuffer.append(URLEncoder.encode(map.get(key),"UTF-8"));
        }
        return stringBuffer.toString();

    }
    public static class Builder{
        private Map<String,String> map;
        public Builder(){
            map = new ArrayMap<>();
        }
        public Builder add(String key, String value){
            if (map.size()>MAX_FORM) throw new IndexOutOfBoundsException("请求参数过多");
            Util.checkMap(key, value);
            map.put(key,value);
            return this;
        }
        public Builder map(Map<String,String> map){
            if (map.size()>MAX_FORM) throw new IndexOutOfBoundsException("请求参数过多");
            this.map = map;
            return this;
        }
        public FormBody build(){
            return new FormBody(this);
        }
    }
}
