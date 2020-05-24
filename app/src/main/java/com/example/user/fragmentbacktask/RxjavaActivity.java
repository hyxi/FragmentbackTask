package com.example.user.fragmentbacktask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Printer;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user.fragmentbacktask.http.CompanyAPI;
import com.example.user.fragmentbacktask.messager.Book;
import com.example.user.fragmentbacktask.utils.LogMonitor;
import com.example.user.fragmentbacktask.utils.eventbus.MessageEvent;
import com.example.user.fragmentbacktask.utils.rxbus.EventMessage;
import com.example.user.fragmentbacktask.utils.rxbus.RxBus;
import com.example.user.fragmentbacktask.view.CrosswiseChart;
import com.example.user.fragmentbacktask.viewmodel.LiveDataActivity;
import com.example.user.fragmentbacktask.viewmodel.LocalServiceActivity;
import com.example.user.fragmentbacktask.workmanager.LocalWorker;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RxjavaActivity extends AppCompatActivity {

    ImageView iv_test;
    private CrosswiseChart cross_chart;
    private RecyclerView rv_state;

    private static final String START = ">>>>> Dispatching";
    private static final String END = "<<<<< Finished";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        iv_test = findViewById(R.id.iv_test);
        rv_state = findViewById(R.id.rv_state);

        findViewById(R.id.btn_view_model).setOnClickListener(view -> {
            startActivity(new Intent(this, LiveDataActivity.class));
        });

        findViewById(R.id.btn_service).setOnClickListener(view -> {
            startActivity(new Intent(this, LocalServiceActivity.class));
        });

        findViewById(R.id.btn_jump).setOnClickListener(view -> {
            startActivity(new Intent(this, CustomTouchEventActivity.class));
        });

        retrofitData();

        UIDetecte();

        cross_chart = findViewById(R.id.cross_chart);

        EventBus.getDefault().register(this);

        EventBus.getDefault().post(new MessageEvent(11));

        testBackpress();

        Parcel parcel = Parcel.obtain();
        parcel.setDataPosition(0);
        for (int i = 0; i < 10; i++) {
            parcel.writeInt(i);
        }
        Logger.d("par1:" + parcel.readInt());


        Intent intent = new Intent();
        intent.setAction("com.example.messager.RemoteService");
        intent.setPackage("com.example.user.fragmentbacktask");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

//        Glide.with(this).load("http://sdfs")
//                .into(iv_test);

        MyReceiver receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.test.broadcast");

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(receiver, filter);

        Intent intent1 = new Intent();
        intent1.setAction("com.example.test.broadcast");
        intent1.putExtra("key", 10000);

        sendBroadcast(intent1);
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                Parcel parcel = Parcel.obtain();
                parcel.writeString("高数");
                Book book = new Book("高数");

                Parcel parcel1 = Parcel.obtain();
                parcel1.writeString("大学语文");
                Book book1 = new Book("大学语文");
                IBookManager iManager = IBookManager.Stub.asInterface(service);
                iManager.addBook(book);
                iManager.addBook(book1);

                List<Book> list = iManager.getBookList();
                Logger.d(list.get(0).getName() + "//" + list.get(1).getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void testBackpress() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Logger.d("receive:" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Handler handler = new Handler();
    }

    class CustomAdapterFactory extends CallAdapter.Factory {


        @Override
        public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
            final CallAdapter<Object, Observable<?>> delegate = (CallAdapter<Object, Observable<?>>)
                    retrofit.nextCallAdapter(this, returnType, annotations);

            return new CallAdapter<Object, Object>() {
                @Override
                public Type responseType() {
                    return delegate.responseType();
                }

                @Override
                public Object adapt(Call<Object> call) {
                    Call<Object> newCall = buildCall(call);
                    return buildObservable(delegate.adapt(newCall), newCall, annotations);
                }
            };
        }

        private Call<Object> buildCall(Call<Object> call) {
            return null;
        }

        private Object buildObservable(Observable<?> observable, Call<Object> call, Annotation[] annotations) {
            return null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventListener(MessageEvent messageEvent) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventsListener(MessageEvent messageEvent) {

    }


    private void test() {

        Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("ss");
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(response -> {
            Logger.d("print:" + response);
        });
    }

    @TargetApi(26)
    private void addWorkerManager() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Duration duration = Duration.of(2, ChronoUnit.SECONDS);

        PeriodicWorkRequest locationWork = new PeriodicWorkRequest.Builder(LocalWorker.class, duration)
                .addTag(LocalWorker.class.getName()).setConstraints(constraints).build();

        WorkManager.getInstance().enqueue(locationWork);
    }

    private void UIDetecte() {
        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                if (START.equals(x)) {
                    LogMonitor.getInstance().startMonitor();
                }
                if (x.startsWith(END)) {
                    LogMonitor.getInstance().removeMonitor();
                }
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("test_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("localData", "test").apply();

        sharedPreferences.getString("localData", "");
    }

    private void retrofitData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weixin.qq.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        CompanyAPI companyAPI = retrofit.create(CompanyAPI.class);
        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(mediaType, "{sdf:sdf}");
        Disposable projectDispose = companyAPI.favProjectModify(1, requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                }, throwable -> {
                    Logger.d(throwable.getMessage());
                });
        Intent intent = new Intent("com.example.testapp");
        PackageManager packageManager = getPackageManager(); // contextWrapper
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        if (resolveInfos.size() == 0) {
            return;
        }
        // 获取制定activity info
        ActivityInfo activityInfo = resolveInfos.get(0).activityInfo;
        String packageName = activityInfo.packageName;
        Logger.d("packageName:" + packageName);
        try {
            Resources res = packageManager.getResourcesForApplication(packageName);
            int id = res.getIdentifier("map_location_c", "drawable", packageName);//根据名字取id
            Logger.d("id:" + id);
//            mImageView.setImageDrawable(res.getDrawable(id));//设置给ImageView
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        CallBack callBack = new CallBack(observer);
        observer.onSubscribe(callBack);
        callBack.print();


        Disposable disposable2 = RxBus.getInstance().tObservable(EventMessage.class)
                .subscribe(response -> {
                    if (response.event.equals("test")) {
                        Logger.d("rxbus 接收到消息://" + response.data);
                    }
                });

        RxBus.getInstance().addSubscription(this, disposable2);
    }

    class CallBack implements Disposable {
        private Observer observer;

        public CallBack(Observer observer) {
            this.observer = observer;
        }

        public void print() {
            observer.onNext("print 1");
        }

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
