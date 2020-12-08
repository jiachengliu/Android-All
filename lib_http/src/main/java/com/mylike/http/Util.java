package com.mylike.http;

/**
 * author: liuxiansheng
 * data: 2020-12-04 15:14
 * desc:
 */
public class Util {
    public static void checkMap(String key,String value){
        if (key==null) throw new NullPointerException("key==null");
        if (key.isEmpty()) throw new NullPointerException("key is empty");
        if (value==null) throw new NullPointerException("value==null");
        if (value.isEmpty()) throw new NullPointerException("value is empty");
    }
    public static void checkMethod(Request.HttpMethed methed,RequestBody body){
        if (methed==null){
            throw new NullPointerException("method == null");
        }
        if (body!=null && Request.HttpMethed.checkNoBody(methed)){
            throw new IllegalStateException("方法"+methed+"不能有请求体");
        }
        if (body == null&&Request.HttpMethed.checkNeedBody(methed)){
            throw new IllegalStateException("方法"+methed+"必须有请求体");
        }

    }
    public static String trans2FileHead(String key, String fileName){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(MultipartBody.disposition)
                .append("name=")
                .append("\"").append(key).append("\"").append(";").append(" ")
                .append("filename=")
                .append("\"").append(fileName).append("\"")
                .append("\r\n");
        return stringBuffer.toString();
    }
    public static String trans2FileHead(String key){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(MultipartBody.disposition)
                .append("name=")
                .append("\"").append(key).append("\"")
                .append("\r\n");
        return stringBuffer.toString();
    }
    public static byte[] getUTF8Byte(String str){
        return str.getBytes();
    }
}
