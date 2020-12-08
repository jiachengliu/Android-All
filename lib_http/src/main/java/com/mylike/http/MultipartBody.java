package com.mylike.http;

import android.provider.Telephony;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * author: liuxiansheng
 * data: 2020-12-04 15:22
 * desc:
 */
public class MultipartBody extends RequestBody{

    public static final String disposition = "content-disposition:form-data";
    public static final byte[] END_LINE = {'\r','\n'};
    public static final byte[] PREFIX = {'-','-'};
    final List<Part> parts;
    final String boundary;
    public MultipartBody(Builder builder){
        this.parts = builder.parts;
        this.boundary = builder.boundary;
    }

    @Override
    String contentType() {
        return "multipart/form-data; boundary"+boundary;
    }

    @Override
    void wirteTo(OutputStream ops) throws IOException {
        try{
            for (Part part: parts){
                ops.write(PREFIX);
                ops.write(boundary.getBytes("UTF-8"));
                ops.write(END_LINE);
                part.write(ops);
            }
        }finally {
            if (ops !=null){
                ops.close();
            }
        }

    }
    public static class Builder{
        private String boundary;
        private List<Part> parts;
        public Builder(){
            this(UUID.randomUUID().toString());
        }
        public Builder(String boundary){
            this.parts = new ArrayList<>();
            this.boundary = boundary;
        }
        public Builder addPart(String type, String key, File file){
            if (key == null) throw new NullPointerException("part name == null");
            parts.add(Part.create(type,key,file));
            return this;

        }
        public Builder addForm(String key, String value){
            if (key == null) throw new NullPointerException("part name == null");
            parts.add(Part.create(key,value));
            return this;
        }
        public MultipartBody build(){
            if (parts.isEmpty()) throw new NullPointerException("part list == null");
            return new MultipartBody(this);
        }
    }
}
