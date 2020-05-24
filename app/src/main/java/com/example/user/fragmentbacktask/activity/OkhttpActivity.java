package com.example.user.fragmentbacktask.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.entity.Test;
import com.example.user.fragmentbacktask.network.okhttp.OkHttpUtils;
import com.example.user.fragmentbacktask.network.okhttp.callback.GenericsCallback;
import com.example.user.fragmentbacktask.network.okhttp.callback.IGenericsSerializable;
import com.example.user.fragmentbacktask.network.okhttp.callback.NewCallback;
import com.example.user.fragmentbacktask.entity.Bean;
import com.example.user.fragmentbacktask.network.okhttp.callback.ResponseEntity;
import com.example.user.fragmentbacktask.entity.one;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpActivity extends Activity {

    private OkHttpClient mOkHttpClient = null;
    //上传文件类型
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private Context mContext;


    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_okhttp);

        String jsUrl = "http://apis.juhe.cn/cook/category?parentid=&dtype=&key=31ddc312fb6981aae1c38fde8a39af99";
        getOkHttpUtils(jsUrl);

    }

    //http://118.192.167.16:9082/http/sf2014.jsp?im_username=
    // l%3Asoufunso9711135&messagename=GetRecentContacts&publicKey
    // =8087D627EBB08B0F889991EDB2FDE26C&wirelesscode=4FAAF87D2EFE39881AAE5C0BDE85629E

    private void getOkHttpUtils(String url) {
        OkHttpUtils.get().url(url).build()// --> RequestCall 配置好Request.Builder
                .execute(new NewCallback<one, Bean>(new IGenericsSerializable()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(ResponseEntity<one> response, int id) {
                        Log.i("bean", "bean://" + ((Bean) response.getBean()).reason);
                        ArrayList<one> list = response.getList();
                        for (int i = 0; i < list.size(); i++) {
                            Log.i("list", "list:" + i + "//" + list.get(i).name);
                        }
                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                    }

                });
    }

    private void getAsyHttp(String url) {

        OkHttpUtils.get().url(url).build()// --> RequestCall 配置好Request.Builder
                .execute(new GenericsCallback<Test>(new IGenericsSerializable()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("error", "eeee");
                    }

                    @Override
                    public void onResponse(Test result, int id) {
                        if (result != null) {
                            Log.i("result", result.resultcode + "//" + result.reason);
                        } else {
                            Log.i("result", "error");
                        }
                    }
                });

        File cacheFile = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(cacheFile.getAbsoluteFile(), cacheSize));


        mOkHttpClient = builder.build();
//        Request.Builder requestBuilder = new Request
//                .Builder().url("http://www.baidu.com");
//        requestBuilder.method("GET" , null);


        RequestBody formBody = new FormBody.Builder().
                add("size", "10").build();
//        Request.Builder requestBuilder = new Request.Builder()
//                .url("http://www.baidu.com")
//                .post(formBody);


        //上传文件
        File file = new File("/sdcard/wangshu.txt");
        Request.Builder requestBuilder = new Request.Builder()
                .url("http://www.baidu.com")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file));


        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    Log.i("wangshu", "cache---" + str);
                } else {
                    response.body().string();
                    String str = response.networkResponse().toString();
                    Log.i("wangshu", "network---" + str);
                }
                Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addheader() {
        final OkHttpClient client = new OkHttpClient();
        

        final Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")

                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("Cookie", "add cookies here")
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void sendMultipart() {
        mOkHttpClient = new OkHttpClient();
    }

    //校验证书，，响应https请求
    public OkHttpClient setCertificates(InputStream... certificates) {
        File cacheFile = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(cacheFile.getAbsoluteFile(), cacheSize));

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

////初始化keystore    使用keytool可生成一个证书请求文件zhy_server.jks，注意密钥库口令为：123456.
//            //双向证书  双向证书验证，比如银行、金融类app。
//            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
//            clientKeyStore.load(mContext.getAssets().open("zhy_client.bks"), "123456".toCharArray());
////Java平台默认识别jks格式的证书文件，但是android平台只识别bks格式的证书文件。
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            keyManagerFactory.init(clientKeyStore, "123456".toCharArray());
//            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
//            mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());

            builder.sslSocketFactory(sslContext.getSocketFactory());
            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

}
