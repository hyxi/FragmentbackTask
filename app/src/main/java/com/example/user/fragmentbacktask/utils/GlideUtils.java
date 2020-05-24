package com.example.user.fragmentbacktask.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Glide封装类
 * Created by litp on 2016/8/6.
 */
public abstract class GlideUtils {

    /**
     * 简单图片加载回调
     *
     * @param <T> 图片url 或资源id 或 文件
     * @param <K> 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
     */
    public interface ImageLoadListener<T, K> {

        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source 图片地址、File、资源id
         * @param e      异常信息
         */
        void onLoadingError(T source, Exception e);

    }


    /**
     * 详细加载图片加载回调
     *
     * @param <T> 图片url 或资源id 或 文件
     * @param <K> 返回的资源
     */
    public interface ImageLoadDetailListener<T, K> {

        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source        图片地址、File、资源id
         * @param errorDrawable 加载错误占位图
         * @param e             异常信息
         */
        void onLoadingError(T source, Drawable errorDrawable, Exception e);

        /**
         * 加载开始
         *
         * @param source      图片来源
         * @param placeHolder 开始加载占位图
         */
        void onLoadingStart(T source, Drawable placeHolder);

    }


    /**
     * 根据上下文和 url获取 Glide的DrawableTypeRequest
     *
     * @param context 上下文
     * @param url     图片连接
     * @param <T>     Context类型
     * @param <K>     url类型
     * @return 返回DrawableTypeRequst<K> 类型
     */
//    private static <T, K> DrawableTypeRequest<K> getDrawableTypeRequest(T context, K url) {
//        DrawableTypeRequest<K> type = null;
//        try {
//            if (context instanceof android.support.v4.app.Fragment) {
//                type = Glide.with((android.support.v4.app.Fragment) context).load(url);
//            } else if (context instanceof android.app.Fragment) {
//                type = Glide.with((android.app.Fragment) context).load(url);
//            } else if (context instanceof Activity) {    //包括FragmentActivity
//                type = Glide.with((Activity) context).load(url);
//            } else if (context instanceof Context) {
//                type = Glide.with((Context) context).load(url);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return type;
//    }
//
//    /**
//     * 图片加载监听类
//     *
//     * @param <T> 图片链接 的类型
//     * @param <K> 图片资源返回类型
//     * @param <Z> 返回的图片url
//     */
//    private static class GlideListener<T, K, Z> implements RequestListener<T, K> {
//
//        ImageLoadListener<Z, K> imageLoadListener = null;
//        Z url;
//        ImageView imageView = null;
//
//        GlideListener(ImageLoadListener<Z, K> imageLoadListener, Z url, ImageView view) {
//            this.imageLoadListener = imageLoadListener;
//            this.url = url;
//            this.imageView = view;
//        }
//
//        GlideListener(ImageLoadListener<Z, K> imageLoadListener, Z url) {
//            this.imageLoadListener = imageLoadListener;
//            this.url = url;
//        }
//
//        GlideListener(Z url) {
//            this.url = url;
//        }
//
//        @Override
//        public boolean onResourceReady(K resource, T model, Target<K> target, boolean isFromMemoryCache, boolean isFirstResource) {
//            if (null != imageLoadListener) {
//                if (imageView != null) {
//                    imageLoadListener.onLoadingComplete(url, imageView, resource);
//                } else {
//                    imageLoadListener.onLoadingComplete(url, null, resource);
//                }
//            }
//
//            return false;
//        }
//
//        @Override
//        public boolean onException(Exception e, T model, Target<K> target, boolean isFirstResource) {
//
//            //LogUtil.e("Glide图片加载失败:"+e + " 地址为:"+url);
//
//            if (imageLoadListener != null) {
//                imageLoadListener.onLoadingError(url, e);
//            }
//            return false;
//        }
//    }
//
//
//    /**
//     * 加载存储器上的图片到目标载体
//     *
//     * @param file      文件File
//     * @param imageView 要显示到的图片ImageView
//     */
//    public static Target<GlideDrawable> loadImage(@NonNull final File file, @NonNull ImageView imageView,
//                                                  ImageLoadListener<File, GlideDrawable> imageLoadListener) {
//        DrawableRequestBuilder builder = getDrawableTypeRequest(imageView.getContext(), file)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用磁盘缓存
//                .skipMemoryCache(true)//跳过内存缓存
//                .dontAnimate();
//        if (imageLoadListener != null) {
//            builder.listener(new GlideListener<File, GlideDrawable, File>(imageLoadListener, file, imageView));
//        }
//        return builder.into(imageView);
//    }
//
//    /**
//     * 加载资源中的图片到目标载体
//     *
//     * @param resourceId 资源id
//     * @param imageView  图片View
//     */
//    public static Target<GlideDrawable> loadImage(@DrawableRes int resourceId, @NonNull ImageView imageView, ImageLoadListener<Integer, GlideDrawable> imageLoadListener) {
//        return getDrawableTypeRequest(imageView.getContext(), resourceId)
//                .listener(new GlideListener<Integer, GlideDrawable, Integer>(imageLoadListener, resourceId, imageView))
//                .into(imageView);
//    }
//
//
//    /**
//     * 加载成圆形头像到普通ImageView，有动画效果
//     *
//     * @param url               图片url
//     * @param imageView         要显示到的ImageView
//     * @param imageLoadListener 加载回调监听器
//     * @param parms             设置占位符和加载失败图片
//     * @return 返回Target<GlideDrawable>
//     */
//    public static Target<GlideDrawable> loadCircleImage(@NonNull String url, @NonNull ImageView imageView, ImageLoadListener<String, GlideDrawable> imageLoadListener, int... parms) {
//        DrawableTypeRequest<String> type = getDrawableTypeRequest(imageView.getContext(), url);
//        if (parms != null && parms.length > 0) {
//            type.placeholder(parms[0]);   //占位符
//            if (parms.length > 1) {
//                type.error(parms[1]);    //图片加载失败显示图片
//            }
//        }
//        type.transform(new CircleTransform(imageView.getContext()));
//        return type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url, imageView))
//                .into(imageView);
//
//    }
//
//
////    /**
////     * 加载网络图片到指定Imageview，支持CircleImageView
////     *
////     * @param url               图片url
////     * @param imageView         要显示的Imageview
////     * @param imageLoadListener 图片加载回调
////     * @param parms             第一个是error的图片
////     */
////    public static <T> Target<GlideDrawable> loadImage(T context, @NonNull String url, @NonNull ImageView imageView, ImageLoadListener<String, GlideDrawable> imageLoadListener, int... parms) {
////        DrawableTypeRequest<String> type = getDrawableTypeRequest(context, url);
////        if (type != null) {
////            type.asBitmap();
////            if (parms != null && parms.length > 0) {
////                type.placeholder(parms[0]);   //占位符
////                if (parms.length > 1) {
////                    type.error(parms[1]);    //图片加载失败显示图片
////                }
////            }
////
////            //单张CircleImageView不允许动画，不然会不显示,
////            if (imageView instanceof CircleImageView) {
////                type.dontAnimate();
////            }
////            return type
////                    .listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url, imageView))
////                    .into(imageView);
////        } else {
////            return null;
////        }
////
////    }
//
//    /**
//     * 加载bitmap，回调返回 Bitmap
//     *
//     * @param context           上下文
//     * @param url               图片url
//     * @param imageLoadListener 图片加载监听器
//     * @param <T>               上下文类型
//     */
//    public static <T> BitmapRequestBuilder<String, Bitmap> loadImageBitmap(T context, @NonNull String url, ImageLoadListener<String, Bitmap> imageLoadListener) {
//        DrawableTypeRequest<String> type = getDrawableTypeRequest(context, url);
//        if (type != null) {
//            if(imageLoadListener != null){
//                return type.asBitmap()
//                        .listener(new GlideListener<String, Bitmap, String>(imageLoadListener, url));
//            }else{
//                return type.asBitmap();
//            }
//
//        } else {
//            return null;
//        }
//    }

    /**
     * 释放内存
     *
     * @param context 上下文
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }


    /**
     * 取消所有正在下载或等待下载的任务。
     *
     * @param context 上下文
     */
    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context 上下文
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }


    /**
     * 清除所有缓存
     *
     * @param context 上下文
     */
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }
}
