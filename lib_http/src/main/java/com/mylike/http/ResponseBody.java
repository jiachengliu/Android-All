package com.mylike.http;

import java.io.UnsupportedEncodingException;

/**
 * author: liuxiansheng
 * data: 2020-12-03 15:56
 * desc:
 */
public class ResponseBody {
    byte[] bytes;
    public ResponseBody(byte[] bytes){
        this.bytes = bytes;
    }
    public byte[] bytes(){
        return this.bytes;
    }
    public String string(){
        try {
            return new String(bytes(),"UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

}
