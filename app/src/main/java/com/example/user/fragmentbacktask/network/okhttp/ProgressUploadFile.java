package com.example.user.fragmentbacktask.network.okhttp;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProgressUploadFile {

    private static final OkHttpClient okHttpClient = new OkHttpClient();

    private void run() {

        MultipartBody.Builder builder = new  MultipartBody.Builder().setType(MultipartBody.FORM);

        File file = new File("D:\\file.jpg");
        builder.addFormDataPart("file", file.getName(), new ProgressRequestBody(
                MultipartBody.FORM, file, new ProgressRequestBody.ProgressListener() {
            @Override
            public void onProgress(long totalBytes, long remainingBytes, boolean done) {
                System.out.print((totalBytes - remainingBytes) * 100 / totalBytes + "%");
            }
        }));

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url("http://localhost:8080/upload") //地址
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("response.body().string() = " + response.body().string());
            }
        });
    }



}
