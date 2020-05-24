package com.example.user.fragmentbacktask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.fragmentbacktask.utils.BitmapUtils;
import com.example.user.fragmentbacktask.utils.ImageUtils;
import com.example.user.fragmentbacktask.utils.StackBlurManager;

import androidx.appcompat.app.AppCompatActivity;

public class BlurPictureActivity extends AppCompatActivity {

    private ImageView picture;
    private TextView statusText;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_picture);
        mContext = this;
        initView();
    }

    private void initView() {
        picture = (ImageView) findViewById(R.id.picture);
        statusText  = (TextView) findViewById(R.id.text);
        applyBlur();
    }

    private void applyBlur() {
        picture.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                picture.getViewTreeObserver().removeOnPreDrawListener(this);
                picture.buildDrawingCache();
//                Bitmap bmp = picture.getDrawingCache();
                Bitmap bitmap = ImageUtils.drawableToBitmap(
                        mContext.getResources().getDrawable(R.mipmap.php));

                blur(bitmap, picture);
                return true;
            }
        });
    }

    private void blur(Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        float radius = 2;
        float scaleFactor = 2;

        Bitmap overlay = Bitmap.createBitmap((int)(view.getMeasuredWidth()/scaleFactor),
                (int)(view.getMeasuredHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.save();
        canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor);//左上角
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        canvas.drawBitmap(bkg, 0, 0, paint);//在0,0坐标开始画入bkg
        canvas.restore();
//        cv.drawBitmap( watermark, w - ww + 5, h - wh + 5, null );//在src的右下角画入水印
        StackBlurManager blurManager = new StackBlurManager(overlay);
        overlay = blurManager.process((int) radius);

        picture.setImageBitmap(overlay);
        statusText.setText("cost " + (System.currentTimeMillis() - startMs) + "ms");

    }

}
