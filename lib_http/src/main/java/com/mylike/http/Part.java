package com.mylike.http;

import android.content.res.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import static com.mylike.http.MultipartBody.END_LINE;

/**
 * author: liuxiansheng
 * data: 2020-12-04 15:27
 * desc:
 */
public abstract class Part {
    private Part(){

    }
    public abstract String contentType();
    public abstract String heads();
    public abstract void write(OutputStream outputStream) throws IOException;

    public static Part create(final String key, final String value){
        return new Part() {
            @Override
            public String contentType() {
                return null;
            }

            @Override
            public String heads() {
                return Util.trans2FileHead(key);
            }

            @Override
            public void write(OutputStream outputStream) throws IOException {
                outputStream.write(heads().getBytes("UTF-8"));
                outputStream.write(END_LINE);
                outputStream.write(value.getBytes("UTF-8"));
                outputStream.write(END_LINE);

            }
        };

    }
    public static Part create(final String type, final String key, final File file){
        if (file == null) throw new NullPointerException("file 为空");
        if (!file.exists()) throw new IllegalStateException("file 不存在");
        return new Part() {
            @Override
            public String contentType() {
                return type;
            }

            @Override
            public String heads() {
                return Util.trans2FileHead(key, file.getName());
            }

            @Override
            public void write(OutputStream outputStream) throws IOException {
                outputStream.write(heads().getBytes());
                outputStream.write("Content-Type:".getBytes());
                outputStream.write(Util.getUTF8Byte(contentType()));
                outputStream.write(END_LINE);
                outputStream.write(END_LINE);
                writeFile(outputStream,file);
                outputStream.write(END_LINE);
                outputStream.flush();

            }
        };

    }
    private static void writeFile(OutputStream outputStream,File file) throws IOException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            int len;
            byte[] bytes = new byte[2048];
            while ((len = inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
        }finally {
            if (inputStream!=null){
                inputStream.close();
            }
        }


    }

}
