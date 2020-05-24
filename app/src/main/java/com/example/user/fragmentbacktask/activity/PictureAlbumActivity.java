package com.example.user.fragmentbacktask.activity;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.entity.PictureInfo;
import com.example.user.fragmentbacktask.view.AlbumViewFipper;

import java.util.ArrayList;

public class PictureAlbumActivity extends AppCompatActivity {

    private AlbumViewFipper vf_pic_album;
    private Context mContext;

    private GestureDetector detector;
    private int selection = 0,listsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_album);
        mContext = this;
        vf_pic_album = (AlbumViewFipper) findViewById(R.id.vf_pic_album);
        fillData();
        registerListener();
    }

    private void fillData() {
        listsize = getPhotoAlbum().size();
        vf_pic_album.setValues(getPhotoAlbum(), 0);
    }

    private void registerListener() {
        detector = new GestureDetector(mContext,new ChatFlingGestureDetector());
    }

    private ArrayList<PictureInfo> getPhotoAlbum() {
        // 设置获取图片的字段信息
        String[] STORE_IMAGES = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, // id
                MediaStore.Images.Media.SIZE
        };
        Cursor cursor = MediaStore.Images.Media.query(getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES, null, STORE_IMAGES[1]);
        ArrayList<PictureInfo> arr = new ArrayList<PictureInfo>();
        while (cursor.moveToNext()) {
            String path = cursor.getString(0);
            String id = cursor.getString(1);
            if (path.endsWith(".JPEG") || path.endsWith(".jpeg") || path.endsWith(".jpg") ||
                    path.endsWith(".JPG") || path.endsWith(".png") || path.endsWith(".PNG")) {
                Log.i("tag", "path===" + path + "====id===" + id);
                if (!path.contains("cache")) {
                    arr.add(new PictureInfo(Integer.valueOf(id), path));
                }
            }
        }
        cursor.close();
        return arr;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean dealResult = vf_pic_album.onTouchEvent(event);
        if(dealResult) return true;
        return detector.onTouchEvent(event);
    }

    private class ChatFlingGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (e1 == null || e2 == null) {
                return false;
            }
            if (e1.getX() - e2.getX() > 80) {//手指从右到左
                selection++;
                if(selection> listsize-1){
                    return false;
                }
                vf_pic_album.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_in));
                vf_pic_album.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_out));
                vf_pic_album.showNext();
                return false;
            } else if (e1.getX() - e2.getX() < -80) {//手指从左到右
                selection--;
                if(selection < 0){
                    selection = 0;
                    return false;
                }
                vf_pic_album.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_in));
                vf_pic_album.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_out));
                vf_pic_album.showPrevious();
                return false;
            }
            return false;
        }
    }

}
