package com.example.user.fragmentbacktask.network.okhttp.builder;

import com.example.user.fragmentbacktask.network.okhttp.request.GetRequest;
import com.example.user.fragmentbacktask.network.okhttp.request.RequestCall;

public class GetBuilder extends OkHttpRequestBuilder {

    @Override
    public RequestCall build() {

      return new GetRequest(url, tag, params, headers,id).build();
    }
}
