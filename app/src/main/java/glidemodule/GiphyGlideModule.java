package glidemodule;

import android.content.Context;

import java.io.File;
import java.io.InputStream;

/**
 * {@link GlideModule} implementation for the Giphy sample app.
 */
public class GiphyGlideModule {
  // implements GlideModule
//  @Override
//  public void applyOptions(final Context context, GlideBuilder builder) {
//    // 设置硬盘缓存
//    builder = new GlideBuilder(context).setDiskCache(new InternalCacheDiskCacheFactory(context,5*1024));
//    //设置缓存路径
//    new GlideBuilder(context).setDiskCache(new DiskCache.Factory(){
//      @Override
//      public DiskCache build() {
//        File cacheLocation = new File(context.getExternalCacheDir(),"glidecache");
//        cacheLocation.mkdirs();
//        return DiskLruCacheWrapper.get(cacheLocation,50*1024);
//      }
//    });
//
//    //内存缓存
//    //默认内存缓存的大小是用过MemorySizeCalculator来实现的，这个类会根据设备屏幕的大小，
//    // 计算出一个合适的size，开发者可以获取到相关的默认设置信息
//    //设置图片格式
//    new GlideBuilder(context).setDecodeFormat(DecodeFormat.PREFER_RGB_565);
//   //bitmappool
//    new GlideBuilder(context).setBitmapPool(new LruBitmapPool(5*1024));
//  }
//
//  @Override
//  public void registerComponents(Context context, Glide glide) {
//    glide.register(Api.GifResult.class,InputStream.class,new GiphyModelLoader.Factory());
//  }
}
