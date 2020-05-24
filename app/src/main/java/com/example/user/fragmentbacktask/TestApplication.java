package com.example.user.fragmentbacktask;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.user.fragmentbacktask.activity.upgrade.UpdateManager;
import com.example.user.fragmentbacktask.activity.upgrade.UpgradeActivity;
import com.example.user.fragmentbacktask.dagger.ApplicationComponent;
import com.example.user.fragmentbacktask.network.okhttp.HttpsUtils;
import com.facebook.stetho.Stetho;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.icon_typeface.Entypo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import androidx.multidex.MultiDex;
import okhttp3.OkHttpClient;

/**
 * Created by user on 2016/5/11.
 */
public class TestApplication extends Application {

    private static TestApplication mApp;
//    public static RefWatcher getRefWatcher(Context context) {
//        TestApplication application = (TestApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

    public static Context mContext;
    private static OkHttpClient mOkHttpClient = null;

    private RefWatcher refWatcher;
    private boolean downloading;
    private UpdateManager mUpdateManager;

    private ApplicationComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mApp = (TestApplication)getApplicationContext();
        mContext = getApplicationContext();

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        Iconics.registerFont(new Entypo());
        Stetho.initializeWithDefaults(this);


        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        refWatcher= setupLeakCanary();
//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(15000L, TimeUnit.MILLISECONDS)
//                .readTimeout(20000L, TimeUnit.MILLISECONDS)
//                .writeTimeout(20000L,TimeUnit.MILLISECONDS)
//                .addInterceptor(new LoggerInterceptor("TAG"))
//                //.cookieJar(cookieJar1)
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                })
//                .build();
        //.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager)


        //SDKInitializer.initialize(mApp);
        initTbs();
    }

    private RefWatcher setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        TestApplication leakApplication = (TestApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    public ApplicationComponent component() {
        return component;
    }

    private void initTbs() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                Log.i("tag","onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.i("tag","onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.i("tag","onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.i("tag","onDownloadProgress:" + i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    public static TestApplication getSelf() {
        return mApp;
    }

    private Handler  mFilterHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent;
            switch (msg.what){
                case 101:
                    intent = new Intent(getBaseContext(), UpgradeActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
            }
        }
    };

    public UpdateManager getUpdateManager() {
        if (mUpdateManager == null) {
            mUpdateManager = new UpdateManager(mFilterHandler);
        }
        return mUpdateManager;
    }

    public boolean isDownloading() {
        return downloading;
    }
    public static Context getContext() {
        return mContext;
    }

    public static OkHttpClient getOkHttpClientInstance(){
        if(mOkHttpClient == null){
            synchronized (TestApplication.class){
                if(mOkHttpClient==null){
                    mOkHttpClient = new OkHttpClient();

                }
            }
        }

        return mOkHttpClient;
    }

}
