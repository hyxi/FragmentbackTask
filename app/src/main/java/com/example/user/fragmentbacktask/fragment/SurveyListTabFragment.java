package com.example.user.fragmentbacktask.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.fragmentbacktask.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by user on 2016/1/11.
 */
public class SurveyListTabFragment extends Fragment {

    public static final String TAG = SurveyListTabFragment.class.getSimpleName();

    private ImageView imageView;
    private Bitmap bitmap;

    public static final class SUB_TAB {
        public static final int SUB_TAB_LEFT = 1;
        public static final int SUB_TAB_MIDDLE = 2;
        public static final int SUB_TAB_RIGHT = 3;
    }

    public static Fragment newInstance() {
        Fragment fragment = new SurveyListTabFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_survey_ll,null);
        imageView = (ImageView) view.findViewById(R.id.survey_imageview);
       // fillData();
        Log.i(TAG, "on onCreateView");
        return view;
    }

    private void fillData() {
        //String imgUri = "/storage/emulated/0/MagazineUnlock/Balance(magazine)-01-2.3.001-bigpicture_01_3.jpg";
       // File file = new File(imgUri);
        try {
            //InputStream in = new FileInputStream(file);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            //BitmapFactory.decodeFile(imgUri, options);
            int height = options.outHeight;
            int width = options.outWidth;
            int h = height / 800;
            int w = width / 600;
            options.inSampleSize = w > h ? w : h;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
           // bitmap =  BitmapFactory.decodeStream(in, null, options);
            BitmapDrawable drawable = new BitmapDrawable(getResources(),bitmap);
            imageView.setImageDrawable(drawable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "on onDetach");
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            System.gc();
        }
    }
}
