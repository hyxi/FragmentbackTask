package com.example.user.fragmentbacktask.utils;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by huangyuxi on 2018/12/27
 * Title: glide 全局配置
 */
//@GlideModule
//public class MyAppGlideModule extends AppGlideModule {
//
//    @Override
//    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
//        super.applyOptions(context, builder);
//        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
//                .setBitmapPoolScreens(3)
//                .build();
//
//        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));
//    }
//
//
//    @Override
//    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//        super.registerComponents(context, glide, registry);
//    }
//}
