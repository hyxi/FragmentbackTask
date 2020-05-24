package glidemodule;

import android.content.Context;
import android.text.TextUtils;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

import androidx.annotation.NonNull;

/**
 * A model loader that translates a POJO mirroring a JSON object representing a single image from
 * Giphy's api into an {@link InputStream} that can be decoded into an
 * {@link android.graphics.drawable.Drawable}.
 */
public class GiphyModelLoader extends BaseGlideUrlLoader<Api.GifResult> {

//  @Override
//  public boolean handles(Api.GifResult model) {
//    return true;
//  }


  @Override
  protected String getUrl(Api.GifResult model, int width, int height, Options options) {
    Api.GifImage fixedHeight = model.images.fixed_height;
    int fixedHeightDifference = getDifference(fixedHeight, width, height);
    Api.GifImage fixedWidth = model.images.fixed_width;
    int fixedWidthDifference = getDifference(fixedWidth, width, height);
    if (fixedHeightDifference < fixedWidthDifference && !TextUtils.isEmpty(fixedHeight.url)) {
      return fixedHeight.url;
    } else if (!TextUtils.isEmpty(fixedWidth.url)) {
      return fixedWidth.url;
    } else if (!TextUtils.isEmpty(model.images.original.url)) {
      return model.images.original.url;
    } else {
      return null;
    }
  }

  @Override
  public boolean handles(@NonNull Api.GifResult gifResult) {
    return false;
  }

  /**
   * The default factory for { com.bumptech.glide.samples.giphy.GiphyModelLoader}.
   */
//  public static class Factory implements ModelLoaderFactory<Api.GifResult, InputStream> {
//
//    @Override
//    public ModelLoader<Api.GifResult, InputStream> build(Context context, GenericLoaderFactory factories) {
//      return new GiphyModelLoader(factories.buildModelLoader(GlideUrl.class,InputStream.class));
//    }
//
//    @Override
//    public void teardown() {
//      // Do nothing.
//    }
//  }

  public GiphyModelLoader(ModelLoader<GlideUrl, InputStream> urlLoader) {
    super(urlLoader);
  }


  private static int getDifference(Api.GifImage gifImage, int width, int height) {
    return Math.abs(width - gifImage.width) + Math.abs(height - gifImage.height);
  }
}
