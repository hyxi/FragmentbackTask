package com.example.user.fragmentbacktask.activity.map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.ChatMsgPositionAdapter;
import com.example.user.fragmentbacktask.entity.ChatPositionInfo;
import com.example.user.fragmentbacktask.utils.ImageUtils;
import com.example.user.fragmentbacktask.utils.StringUtils;
import com.example.user.fragmentbacktask.view.GeneralDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import androidx.core.content.ContextCompat;

/**
 * 1.地理位置反编码 GeoCoder
 * 2.poi搜索，PoiSearch 附近Near
 *mPoiSearch.searchInBound()
 mPoiSearch.searchNearby();
 mPoiSearch.searchInCity() ;
 mPoiSearch.searchPoiIndoor()
 mPoiSearch.searchPoiDetail()

 * 3.定位 LocationClient BDLocationListener服务监听
 * 4.百度地图截图，在图片中间添加大头针
 * 5.listview.setselection() listview.post()
 */
public class BMapSearchActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "BMapSearchActivity";

    private TextView btn_back;
    private EditText et_position_search;
    private BaiduMap mBaiduMap;
    private TextView tv_send, tv_search_cancel, tv_reset_point;
    private ImageView iv_bottom_refresh;
    private ImageView iv_clear_input;
    private MapView mapView;
    private ListView lv_postion_list;
    private ChatMsgPositionAdapter positionAdapter;

    private Context mContext;

    private FrameLayout fl_content;

    private ArrayList<ChatPositionInfo> positionList;

    private PoiSearch mPoiSearch = null;
    private GeoCoder mGeoCodSearch = null; // 搜索模块，也可去掉地图模块独立使用

    private LocationClient mLocationClient;

    private double oldLon;
    private double oldLat;
    private double currentLon;
    private double currentLat;
    private int currentPosition = 0;
    private String locCity;//定位城市
    private String relateCity;//切换城市
    private boolean isLvClick = false;
    private boolean isMapshow = true;
    private String filePath = null; //保存地图图片路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);
        mContext = this;
        relateCity = "北京";
        initView();
        initMapData();
        registerListener();

        PackageManager pm = getPackageManager();

        //判断应用是否具有某个权限
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION
        )){
            Log.i("loc","true");
        }else{
            Log.i("loc","false");
        }

    }

    public  boolean isOPen() {
        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);
        try{
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            return gps && network;
        }catch (Exception ex){
            return false;
        }
    }


    private void initView() {
        btn_back = (TextView) findViewById(R.id.btn_back);
        tv_send = (TextView) findViewById(R.id.tv_send);
        iv_clear_input = (ImageView) findViewById(R.id.iv_clear_input);
        iv_bottom_refresh = (ImageView) findViewById(R.id.iv_bottom_refresh);

        et_position_search = (EditText) findViewById(R.id.et_position_search);
        tv_search_cancel = (TextView) findViewById(R.id.tv_search_cancel);
        tv_reset_point = (TextView) findViewById(R.id.tv_reset_point);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);

        mapView = (MapView) findViewById(R.id.map_view_chat_position);
        lv_postion_list = (ListView) findViewById(R.id.lv_postion_list);
        positionAdapter = new ChatMsgPositionAdapter(this);
        lv_postion_list.setAdapter(positionAdapter);

        et_position_search.clearFocus();
    }

    private void registerListener() {
        btn_back.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        iv_clear_input.setOnClickListener(this);
        tv_search_cancel.setOnClickListener(this);
        tv_reset_point.setOnClickListener(this);
        et_position_search.setOnClickListener(this);
        et_position_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable cs) {
                if (cs.length() <= 0) {
                    return;
                }
                PoiCitySearchOption option = new PoiCitySearchOption();
                option.keyword(cs.toString());
                String city = StringUtils.isNullOrEmpty(locCity) ? relateCity : locCity;
                option.city(city);
                option.pageCapacity(15);
                mPoiSearch.searchInCity(option);
            }
        });

        lv_postion_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ChatPositionInfo chatPosInfo = positionList.get(position);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(chatPosInfo.location);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if(fl_content.getVisibility() == View.GONE){
                    tv_search_cancel.setVisibility(View.GONE);
                    fl_content.setVisibility(View.VISIBLE);
                    et_position_search.setText("");

                    //隐藏软键盘
                    InputMethodManager mgr = (InputMethodManager) mContext
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(et_position_search.getWindowToken(), 0);

                    lv_postion_list.setVisibility(View.GONE);
                    iv_bottom_refresh.setVisibility(View.VISIBLE);
                    tv_send.setVisibility(View.VISIBLE);
                    isMapshow = true;
                }else{
                    isLvClick = true;
                    chatPosInfo.isChoose = true;
                    positionList.get(currentPosition).isChoose = false;
                    positionAdapter.notifyDataSetChanged();
                }
                currentPosition = position;
            }
        });
    }

    private void initMapData() {
        hideZoomControl();
        mBaiduMap = mapView.getMap();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(15));
        mBaiduMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());
        mPoiSearch = PoiSearch.newInstance();
        mGeoCodSearch = GeoCoder.newInstance();

        getLocation();
        registerMapLister();
    }

    /**
     * 隐藏缩放控件
     */
    private void hideZoomControl() {
        int childCount = mapView.getChildCount();
        View zoom = null;
        for (int i = 0; i < childCount; i++) {
            View child = mapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                zoom = child;
                break;
            }
        }
        if (zoom != null)
            zoom.setVisibility(View.GONE);
    }

    private void registerMapLister() {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                if (!isLvClick && isMapshow) {
                    LatLng mCenterLatLng = mapStatus.target;
                    /**获取经纬度*/
                    currentLat = mCenterLatLng.latitude;
                    currentLon = mCenterLatLng.longitude;
                    LatLng ptCenter = new LatLng(currentLat, currentLon);
                    iv_bottom_refresh.setVisibility(View.VISIBLE);
                    lv_postion_list.setVisibility(View.GONE);
                    mGeoCodSearch.reverseGeoCode(new ReverseGeoCodeOption()
                            .location(ptCenter));
                    isLvClick = false;
                }else{
                    isLvClick = false;
                }
            }
        });
        //地理位置反编码 获取周边位置
        mGeoCodSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {}

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                }
                positionList = new ArrayList<>();
                if (result.getPoiList() != null && result.getPoiList().size() > 0) {
                    for (int i = 0; i < result.getPoiList().size(); i++) {
                        PoiInfo info = result.getPoiList().get(i);

                        ChatPositionInfo chatPosInfo = new ChatPositionInfo();
                        chatPosInfo.place = info.name;
                        chatPosInfo.address = info.address;
                        chatPosInfo.location = info.location;
                        positionList.add(chatPosInfo);
                        if (i == 0) {
                            chatPosInfo.isChoose = true;
                            currentPosition = 0;
                        }
                    }
                }
                Log.i("geo","ismapshow://"+isMapshow);
                if(isMapshow) {
                    positionAdapter.resetData(positionList);
                    iv_bottom_refresh.setVisibility(View.GONE);

                    lv_postion_list.setVisibility(View.VISIBLE);
                    lv_postion_list.post(new Runnable() {
                        @Override
                        public void run() {
                            lv_postion_list.setSelection(0);
                        }
                    });
                }


            }
        });
        //poi关联搜索
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult != null) {
                    positionList = new ArrayList<>();
                    if (poiResult.getAllPoi() != null && poiResult.getAllPoi().size() > 0) {
                        for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                            PoiInfo info = poiResult.getAllPoi().get(i);
                            ChatPositionInfo chatPosInfo = new ChatPositionInfo();
                            chatPosInfo.place = info.name;
                            chatPosInfo.address = info.address;
                            chatPosInfo.location = info.location;
                            positionList.add(chatPosInfo);
                        }
                    }

                    if(!isMapshow) {
                        positionAdapter.resetData(positionList);
                        lv_postion_list.post(new Runnable() {
                            @Override
                            public void run() {
                                lv_postion_list.setSelection(0);
                            }
                        });

                        if (lv_postion_list.getVisibility() == View.GONE) {
                            lv_postion_list.setVisibility(View.VISIBLE);
                            lv_postion_list.post(new Runnable() {
                                @Override
                                public void run() {
                                    lv_postion_list.setSelection(0);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        });
    }

    public void getLocation() {
        //注册监听
        mLocationClient.registerLocationListener(mBDListener);
        //声明定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setOpenGps(true);//设置是否使用gps
        option.setLocationNotify(false);//设置是否当gps有效时按照1S1次频率输出GPS结果
        option.SetIgnoreCacheException(false);//设置是否收集CRASH信息
        option.setEnableSimulateGps(false);//设置是否需要过滤gps仿真结果
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.tv_send:
                send();
                tv_send.setEnabled(false);
                break;
            case R.id.tv_search_cancel:
                et_position_search.setText("");
                if (fl_content.getVisibility() == View.GONE) {
                    fl_content.setVisibility(View.VISIBLE);
                    isMapshow = true;
                    lv_postion_list.setVisibility(View.GONE);
                    iv_bottom_refresh.setVisibility(View.VISIBLE);
                    tv_send.setVisibility(View.VISIBLE);
                }
                //隐藏软键盘
                InputMethodManager mgr = (InputMethodManager) mContext
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(et_position_search.getWindowToken(), 0);

                tv_search_cancel.setVisibility(View.GONE);

                LatLng currentLL = new LatLng(currentLat,currentLon);
                mGeoCodSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(currentLL));
                et_position_search.setCursorVisible(false);
                break;
            case R.id.iv_clear_input:
                String inputStr = et_position_search.getText().toString();
                et_position_search.setText("");
                if(inputStr.length()>0){
                    String s = inputStr.substring(0,1);
                    PoiCitySearchOption option = new PoiCitySearchOption();
                    option.keyword(s);
                    String city = StringUtils.isNullOrEmpty(locCity) ? relateCity : locCity;
                    option.city(city);
                    option.pageCapacity(15);
                    mPoiSearch.searchInCity(option);
                }
                break;

            case R.id.et_position_search:
                if (fl_content.getVisibility() == View.VISIBLE) {
                    fl_content.setVisibility(View.GONE);
                    iv_bottom_refresh.setVisibility(View.GONE);
                    lv_postion_list.setVisibility(View.GONE);
                    tv_search_cancel.setVisibility(View.VISIBLE);
                    tv_send.setVisibility(View.GONE);
                    isMapshow = false;
                }
                Log.i("et","ismapshow://"+isMapshow);
                et_position_search.setCursorVisible(true);

                break;
            case R.id.tv_reset_point:
                LatLng oldLL = new LatLng(oldLat, oldLon);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(oldLL);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                break;
        }
    }

    private void send() {
        String fileMkr = Environment.getExternalStorageDirectory()+File.separator+"maptest";
        File fileDir = new File(fileMkr);
        if(!fileDir.exists())
            fileDir.mkdir();
        filePath = fileDir.getPath()+ File.separator + System.currentTimeMillis() + ".jpg";

        mBaiduMap.setMyLocationEnabled(false);


        int width = mapView.getWidth();
        int height = mapView.getHeight();

        int totalWidth = StringUtils.dp2px(229);
        int totalHeight = StringUtils.dp2px(133);

        final Rect rect = new Rect((width - totalWidth) / 2, (height - totalHeight) / 2,
                (width +totalWidth)/2, (height +totalHeight)/2);
        mBaiduMap.snapshotScope(rect, new BaiduMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                File file = new File(filePath);
                FileOutputStream out;

                int scale = calculateInSampleSize(bitmap.getWidth(),bitmap.getHeight(),400,300);
                int bWidth = bitmap.getWidth()/scale;
                int bHeight = bitmap.getHeight()/scale;

                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.chat_location_poi);
                Bitmap bitmapPoint = ImageUtils.drawableToBitmap(drawable);
                Bitmap bitmapNew = Bitmap.createBitmap(bWidth, bHeight, Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmapNew);
                Rect rectO = new Rect(0,0,bWidth, bHeight);
                Rect rectF = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
                canvas.drawBitmap(bitmap, rectF,rectO, null);

                int height = StringUtils.dp2px(6);
                scale +=1;
                Rect rectM = new Rect((bWidth- bitmapPoint.getWidth()/scale)/2,(bHeight- bitmapPoint.getHeight()/scale)/2-height,
                        (bWidth+bitmapPoint.getWidth()/scale)/2,(bHeight+ bitmapPoint.getHeight()/scale)/2-height);
                Rect rectP = new Rect(0,0,bitmapPoint.getWidth(),bitmapPoint.getHeight());
                canvas.drawBitmap(bitmapPoint, rectP, rectM, null);

                try {
                    out = new FileOutputStream(file);
                    if (bitmapNew.compress(Bitmap.CompressFormat.PNG, 30, out)) {
                        out.flush();
                        out.close();
                    }
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //计算图片压缩比例
    public int calculateInSampleSize(int origWidth,int origHeight,
                                     int targetWidth,int targetHeight){
        if(targetWidth>origWidth && targetHeight>origHeight){
            return 1;
        }else{
            int ws = origWidth/targetWidth;
            int hs = origHeight/targetHeight;
            if(ws>hs){
                if(origWidth%targetWidth>targetWidth/2){
                    return ws + 1;
                } else{
                    return ws;
                }
            }else{
                if(ws == hs){
                    if(origHeight % targetHeight > targetHeight / 2|| origWidth%targetWidth>targetWidth/2){
                        return ws+1;
                    }else{
                        return ws;
                    }
                }else {
                    if (origHeight % targetHeight > targetHeight / 2) {
                        return hs + 1;
                    } else {
                        return hs;
                    }
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationClient != null)
            mLocationClient.start();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
        // 取消监听函数
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDListener);
            // 退出时销毁定位
            mLocationClient.stop();
        }
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mBaiduMap = null;
    }

    /**
     * 定位结果回调，重写onReceiveLocation方法
     */
    private BDLocationListener mBDListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                oldLon = location.getLongitude();
                oldLat = location.getLatitude();
                locCity = location.getCity();

                mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.NORMAL,true,null));
                MyLocationData locationData = new MyLocationData.Builder()
                        .accuracy(0).direction(0).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();

                mBaiduMap.setMyLocationData(locationData);

                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll);

                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                if (mLocationClient.isStarted()) {
                    // 获得位置之后停止定位
                    mLocationClient.stop();
                }
            }
        }
    };

    private void showNoLocDialog() {
        GeneralDialog.Builder builder = new GeneralDialog.Builder(mContext);

        builder.setTitle("打开“定位服务”来允许房天下确定你的位置");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 100);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        GeneralDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
