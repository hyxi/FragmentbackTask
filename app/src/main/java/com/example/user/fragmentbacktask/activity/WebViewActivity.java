package com.example.user.fragmentbacktask.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.GeneralDialog;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class WebViewActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back, iv_fav, iv_share;
    private TextView tv_title;
    private WebView wv_content;
    private Context mContext;

    private String url;
    // 分享view
    private PopupWindow sharePopup;

    private String share_content = "";// 分享内容
    private String share_url = "";// 分享URL
    private String share_logourl = "";// 分享头图URL
    private String share_title = "";// 分享标题
    private Boolean forcedReturn = false;//返回是否需要强制关闭

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mContext = this;
        initView();
        fetchIntent();
        registerListener();
        wv_content.loadUrl(url);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_fav = (ImageView) findViewById(R.id.iv_fav);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        tv_title = (TextView) findViewById(R.id.tv_title);
        wv_content = (WebView) findViewById(R.id.webview);

        Log.i("luoxi", "getX5WebViewExtension--> " + wv_content.getX5WebViewExtension());
        wv_content.requestFocusFromTouch();
        wv_content.setDownloadListener(new MyWebViewDownLoadListener());
    }

    private void fetchIntent() {
        String title = getIntent().getStringExtra("title");
        tv_title.setText(title);

        url = getIntent().getStringExtra("url");// 展示url
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_fav:
                break;
            case R.id.iv_share:
                break;
        }
    }

    private void registerListener() {
        iv_back.setOnClickListener(this);
        iv_fav.setOnClickListener(this);
        iv_share.setOnClickListener(this);

        wv_content.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        wv_content.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                share_url = "";
                share_content = "";
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("msg", "onPageStarted!" + url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                sslErrorHandler.proceed();//等待证书响应，用于支持https协议
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("msg", "shouldOverrideUrlLoading:" + url);
                // 跳到新页面时，清空上一个页面的分享数据
                share_content = "";
                share_url = "";
                share_logourl = "";
                share_title = "";
                forcedReturn = false;

                PackageManager pm = mContext.getPackageManager();

                if (url.startsWith("tel:")) {
                    String tel = url.replace("转", ",");
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(tel));
                    startActivity(intent);
                    return true;
                } else if (url.startsWith("wtai:")) {
                    if(PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.CALL_PHONE",mContext.getPackageName())){
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url.replace(
                                "wtai://wp/mc;", "tel:")));
                        startActivity(intent);
                    }
                    return true;
                } else if (url.startsWith("alipays:")) {
                    try {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        new GeneralDialog.Builder(mContext).setMessage("未检测到支付宝客户端，请安装后再试。")
                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, alipayUrl));
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }
                } else {
//                    try {
//                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }
        });
        wv_content.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                try {
                    if (message.contains("请重试")) {
                        message += "?";
                    }
                    GeneralDialog.Builder builder = new GeneralDialog.Builder(
                            WebViewActivity.this).setTitle("提示").setMessage(message)
                            .setPositiveButton("确定", new GeneralDialog.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.create();
                    builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.i("msg", "onJsConfirm");
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue, JsPromptResult result) {
                Log.i("msg", "onJsPrompt");
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.i("progress",newProgress+"");
            }

            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {

            }
        });
    }

    /**
     * 下载文件监听事件，跳转到系统自带浏览器下载
     */
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

}
