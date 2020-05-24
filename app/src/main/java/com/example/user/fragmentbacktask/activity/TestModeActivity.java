package com.example.user.fragmentbacktask.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.entity.ChatPositionInfo;
import com.example.user.fragmentbacktask.utils.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;

import static com.example.user.fragmentbacktask.R.id.loc;

public class TestModeActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mode);
        mContext = this;
        initView();

    }

    private void initView() {
        Button locBtn = (Button) findViewById(loc);

        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawableBg = mContext.getResources().getDrawable(R.mipmap.php);
                Bitmap bitmapBg = ImageUtils.drawableToBitmap(drawableBg);

                Bitmap bitmapNew = Bitmap.createBitmap(bitmapBg.getWidth(),bitmapBg.getHeight(),bitmapBg.getConfig());
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.chat_location_poi);
                Bitmap bitmapPoint = ImageUtils.drawableToBitmap(drawable);

                Canvas canvas = new Canvas(bitmapNew);
                canvas.drawBitmap(bitmapBg,new Matrix(),null);
                canvas.drawBitmap(bitmapPoint,bitmapBg.getWidth()/2,bitmapBg.getHeight()/2,null);

                String fileMkr = Environment.getExternalStorageDirectory()+File.separator+"maptest";
                File fileDir = new File(fileMkr);
                if(!fileDir.exists())
                    fileDir.mkdir();
                String filePath = fileDir.getPath()+ File.separator + System.currentTimeMillis() + ".jpg";

                File file = new File(filePath);
                FileOutputStream out;

                try {
                    out = new FileOutputStream(file);
                    if (bitmapNew.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                        out.flush();
                        out.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // SoufunApp.toastMgr.builder.display("发送出错，请重新发送！", Toast.LENGTH_LONG);
                }
            }
        });
    }
}
