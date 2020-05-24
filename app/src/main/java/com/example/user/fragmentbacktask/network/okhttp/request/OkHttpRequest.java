package com.example.user.fragmentbacktask.network.okhttp.request;

import com.example.user.fragmentbacktask.network.okhttp.callback.Callback;
import com.example.user.fragmentbacktask.network.okhttp.utils.TExceptions;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class OkHttpRequest {
    public String url;
    public Object tag;
    public Map<String, String> params;
    public Map<String, String> headers;
    public int id;

    protected Request.Builder builder = new Request.Builder();

    public OkHttpRequest(String url, Object tag,
                            Map<String, String> params, Map<String, String> headers,int id) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.id = id;

        if(url == null){
            TExceptions.illegalArgument("url must not be null! ");
        }
        initBuilder();
    }

    private void initBuilder() {
        builder.url(url).tag(tag);
        appendHeaders();
    }

    private void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    public abstract RequestBody buildRequestBody();

    public abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build(){
        return new RequestCall(this);
    }

    public Request generateRequest(Callback callback){
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        return  buildRequest(wrappedRequestBody);
    }


    public RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    public int getId(){
        return id;
    }

}
