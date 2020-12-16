package com.mylike.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

/**
 * author: liuxiansheng
 * data: 2020-12-04 09:40
 * desc:
 */
public class RequestHandler implements IRequestHandler{



    @Override
    public Response handlerRequest(HttpCall call) throws IOException {
        HttpURLConnection connection = mangeConfig(call);
        if (!call.request.heads.isEmpty()) addHeaders(connection,call.request);
        if (call.request.body!=null) writeContent(connection,call.request.body);
        if (!connection.getDoOutput()) connection.connect();
        int responseCode = connection.getResponseCode();
        Log.e("CHttp-responseCode:",responseCode+"");
        if (responseCode>=200&&responseCode<400){
            byte[] bytes = new byte[1024];
            int len;
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((len = inputStream.read(bytes))!=-1){
                byteArrayOutputStream.write(bytes,0,len);
            }
            Response response = new Response.Builder()
                    .code(responseCode)
                    .message(connection.getResponseMessage())
                    .body(new ResponseBody(byteArrayOutputStream.toByteArray()))
                    .build();

            try {
                inputStream.close();
                connection.disconnect();
            }finally {
                if (inputStream!=null) inputStream.close();
                if (connection!=null) connection.disconnect();
            }
            return response;


        }
        throw new IOException(String.valueOf(connection.getResponseCode()));
    }

    private HttpURLConnection mangeConfig(HttpCall call)throws IOException{

        URL url = new URL(call.request.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(call.config.connTimeout);
        connection.setReadTimeout(call.config.readTimeout);
        connection.setDoInput(true);
        if (call.request.body!=null&&Request.HttpMethed.checkNeedBody(call.request.methed)){
            connection.setDoOutput(true);
        }
        return connection;


    }
    private void writeContent(HttpURLConnection connection,RequestBody body)throws IOException{
        OutputStream outputStream = connection.getOutputStream();
        body.wirteTo(outputStream);

    }

    private void addHeaders(HttpURLConnection connection,Request request){
        Set<String> keys = request.heads.keySet();
        for (String key:keys){
            connection.addRequestProperty(key,request.heads.get(key));
        }
    }
}
