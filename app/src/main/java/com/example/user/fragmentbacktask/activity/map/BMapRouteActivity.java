package com.example.user.fragmentbacktask.activity.map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.IntentUtils;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class BMapRouteActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_route, btn_open_bdmap;
    private MapView mv_route;
    private BaiduMap mBaiduMap;
    private Context mContext;

    private LocationClient mLocationClient;
    private double stLon, stLat;

    private double endLat, endLon;
    // 搜索相关
    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
    private List<DrivingRouteOverlay> overlayList = new ArrayList<>();
    private boolean isRouteShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmap_route);
        mContext = this;
        initView();
        initMap();
    }

    private void initView() {
        btn_route = (Button) findViewById(R.id.btn_route);
        btn_open_bdmap = (Button) findViewById(R.id.btn_open_bdmap);

        btn_route.setOnClickListener(this);
        btn_open_bdmap.setOnClickListener(this);
        mv_route = (MapView) findViewById(R.id.mv_route);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_route:
                if (isRouteShow) {
                    for (DrivingRouteOverlay overlay : overlayList) {
                        overlay.removeFromMap();
                    }
                    overlayList.clear();
                    btn_route.setText("显示路线");
                    isRouteShow = false;
                } else {

                    DrivingRoutePlanOption option = new DrivingRoutePlanOption();
                    Log.i("point","start： "+stLat+"//"+stLon);
                    option.from(PlanNode.withLocation(new LatLng(stLat, stLon)));
                    Log.i("point","end： "+endLat+"//"+endLon);
                    option.to(PlanNode.withLocation(new LatLng(endLat, endLon)));
                    //时间最少
                    option.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_TIME_FIRST);
                    option.currentCity("北京");
                    mSearch.drivingSearch(option);
                }
                break;
            case R.id.btn_open_bdmap:

                startRoutePlanDriving();

                break;
        }
    }

    private static String AmapPkg = "com.autonavi.minimap";// 高德地图
    private static String BmapPkg = "com.baidu.BaiduMap";// 百度地图

    private void startRoutePlanDriving(){
        try {
//            if (IntentUtils.isInstall(mContext, BmapPkg)) {
//
//                //行车路线
////                String url = "http://api.map.baidu.com/direction? origin=" +
////                        +stLat+","+stLon+"&destination=" +endLat+","+endLon+
////                        "&mode=driving&output =";
////                Uri uri = Uri.parse(url);
////                Intent intent = new Intent(Intent.ACTION_VIEW);
////                intent.setData(uri);
////                intent.setPackage(BmapPkg);
//
//                //导航
//                String uriStr =  "intent://map/direction?origin=name:从这里开始|latlng:" +
//                        stLat+","+stLon+"&destination=name:到这里结束|latlng:"+endLat+","+endLon+
//                        "&coord_type =bd09ll&mode=navigation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
//
//                Intent intent = Intent.parseUri(uriStr,0);
//
////                StringBuffer sb = new StringBuffer("intent://map/marker?location=" + endLat + "," + endLon);
////                sb.append("&title=");
////                sb.append("");
////                sb.append("&src=soufun#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
////                Intent intent = Intent.getIntent(sb.toString());
//                startActivity(intent);
//            }

            if (IntentUtils.isInstall(mContext, AmapPkg)) {// 调取高德地图应用
                LatLng stLL = bd_decrypt(stLat,stLon);
                LatLng endLL = bd_decrypt(endLat,endLon);

                //http://lbs.amap.com/api/uri-api/android-uri-explain/
               String dat="androidamap://route?sourceApplication=FragmentbackTask&slat="+stLL.latitude+"&slon=" +
                       stLL.longitude+"&sname=&"+
                "dlat="+endLL.latitude+"&dlon="+endLL.longitude+"&dname=&dev=0&m=0&t=2";

//                StringBuffer dat = new StringBuffer("androidamap://viewMap?sourceApplication=soufun");
//                dat.append("&poiname=");
//                dat.append("");
//                dat.append("&lat=");
//                dat.append(endLL.latitude);
//                dat.append("&lon=");
//                dat.append(endLL.longitude);
//                dat.append("&dev=0");
                /**
                 * dev: style:导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6
                 * 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
                 *
                 **/
                Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(
                        dat.toString()));
                intent.setPackage(AmapPkg);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            // 构建 route搜索参数
//            RouteParaOption para = new RouteParaOption()
//                    .startPoint(new LatLng(stLat, stLon)).endPoint(new LatLng(endLat, endLon));
////          .startName("天安门")
////          .endPoint(ptEnd);
//            try {
//                BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, this);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.i("tag", "未安装百度地图");
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    LatLng bd_decrypt(double bd_lat, double bd_lon) {

        double x = bd_lon - 0.0065, y = bd_lat - 0.006;

        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);

        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double gg_lon = z * Math.cos(theta);

        double gg_lat = z * Math.sin(theta);
        return new LatLng(gg_lat,gg_lon);

    }


    private void initMap() {
        mBaiduMap = mv_route.getMap();
        mLocationClient = new LocationClient(getApplicationContext());
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(15));
        mBaiduMap.setMyLocationEnabled(true);

        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    return;
                }

                switch (result.error) {
                    case NO_ERROR:
                        break;
                    case NETWORK_ERROR:
                        Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
                        return;
                    case NETWORK_TIME_OUT:
                        Toast.makeText(mContext, "网络超时", Toast.LENGTH_SHORT).show();
                        return;
                    case ST_EN_TOO_NEAR:
                        Toast.makeText(mContext, "起终点太近", Toast.LENGTH_SHORT).show();
                        return;
                    default:
                        Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
                        return;
                }

                if (result.getRouteLines().get(0) == null) {
                    //rl_routeplan.setVisibility(View.GONE);
                    Toast.makeText(mContext, "由于距离太远，无法提供有效的路线方案",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (result.getRouteLines().size() > 1) {
                    List<DrivingRouteLine> routeLines = result.getRouteLines();
                    for (DrivingRouteLine rline : routeLines) {
                        DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                        overlay.setData(rline);
                        overlay.addToMap();
                        overlay.zoomToSpan();
                        overlayList.add(overlay);
                        isRouteShow = true;
                    }

                } else if (result.getRouteLines().size() == 1) {
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                    overlayList.add(overlay);
                    isRouteShow = true;
                }
                if (isRouteShow) {
                    btn_route.setText("隐藏路线");
                } else {
                    btn_route.setText("显示路线");
                }
            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                endLat = mapStatus.target.latitude;
                endLon = mapStatus.target.longitude;
            }
        });
        getLocation();
    }


//    // 定制RouteOverly
//    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {
//
//        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        @Override
//        public BitmapDescriptor getStartMarker() {
//            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//            }
//            return null;
//        }
//
//        @Override
//        public BitmapDescriptor getTerminalMarker() {
//            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//            return null;
//        }
//    }

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

    /**
     * 定位结果回调，重写onReceiveLocation方法
     */
    private BDLocationListener mBDListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                stLat = location.getLatitude();
                stLon = location.getLongitude();


                mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.NORMAL, true, null));
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


    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mv_route.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationClient != null)
            mLocationClient.start();
        mv_route.onResume();
    }

    @Override
    protected void onDestroy() {
        mv_route.onDestroy();
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
}
