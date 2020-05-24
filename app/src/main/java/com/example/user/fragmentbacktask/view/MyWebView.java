package com.example.user.fragmentbacktask.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.user.fragmentbacktask.utils.StringUtils;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

public class MyWebView extends WebView{
    Context mContext;

    public MyWebView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    @SuppressLint("NewApi")
    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);// 支持js脚本
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);// 是否保存表单数据
        webSettings.setSavePassword(false);// 是否保存密码
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);//支持js插件
        webSettings.setSupportZoom(true);// 是否支持缩放
        webSettings.setSupportMultipleWindows(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);// 提高渲染的优先级
        webSettings.setBlockNetworkImage(false);// 把图片加载放在最后来加载渲染
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);//媒体自动播放
        }
        webSettings.setGeolocationEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setLoadWithOverviewMode(true);
        /***打开本地缓存提供JS调用**/
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*8);
        try{
            String appCachePath = mContext.getCacheDir().getAbsolutePath();
            webSettings.setAppCachePath(appCachePath);
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
        } catch(Exception e){
            e.printStackTrace();
        }
        setLongClickable(true);
        setScrollbarFadingEnabled(true);
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

    }

    @Override
    public void loadUrl(String url) {

        if (StringUtils.isNullOrEmpty(url)) {
            return;
        }
        super.loadUrl(url);
    }
}
