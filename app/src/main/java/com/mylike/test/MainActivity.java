package com.mylike.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mylike.http.HttpClient;
import com.mylike.http.Request;
import com.mylike.http.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final HttpClient client= new HttpClient.Builder()
                .build();
//        MyClass
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                client.newCall(request).execute();
                Response response = client.newCall(request).execute();
                if (response.code==200){
                    Log.e("MainActivity:","response msg "+response.body.string());
                }
            }
        });
    }
}
