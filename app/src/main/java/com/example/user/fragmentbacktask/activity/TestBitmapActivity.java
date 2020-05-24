package com.example.user.fragmentbacktask.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.user.fragmentbacktask.R;

import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class TestBitmapActivity extends AppCompatActivity {

    private static ImageView iv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_btimap);
        iv_content = (ImageView) findViewById(R.id.iv_content);
        initView();
        showImage();
    }

    private void initView() {
//        Drawable drawable=getResources().getDrawable(R.mipmap.header_bg);
//        Bitmap bitmap = ImageUtils.drawableToBitmap(drawable);
//        iv_content.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(getResources(),
//                R.mipmap.header_bg,200,150));
    }

    private void showImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap_bg = BitmapFactory.decodeResource(getResources(), R.drawable.chat_adapter_to_bg);
                InputStream in = getResources().openRawResource(R.raw.header_bg);
                Bitmap bitmap_in = BitmapFactory.decodeStream(in);
                final Bitmap bp = getRoundCornerImage(bitmap_bg, bitmap_in);
//                final Bitmap bp2 = getShardImage(bitmap_bg, bitmap_in);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv_content.setImageBitmap(bp);
                    }
                });
            }
        }).start();
    }

    public static Bitmap getRoundCornerImage(Bitmap bitmap_bg, Bitmap bitmap_in) {

        int width  = iv_content.getWidth()/3;
        int height = iv_content.getHeight()/3;

        Bitmap roundConcerImage = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);//图片显示位置
        Rect rectF = new Rect(100, 0, bitmap_in.getWidth(), bitmap_in.getHeight());//绘制图片矩形区域
        paint.setAntiAlias(true);
       // NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        //patch.draw(canvas, rect);
       // paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap_in, rectF, rect, paint);
        return roundConcerImage;
    }

    public static Bitmap getShardImage(Bitmap bitmap_bg, Bitmap bitmap_in) {
        Bitmap roundConcerImage = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, 300, 300);
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        Rect rect2 = new Rect(0, 0, 300, 300);
        canvas.drawBitmap(bitmap_in, rect, rect2, paint);
        return roundConcerImage;
    }

}
