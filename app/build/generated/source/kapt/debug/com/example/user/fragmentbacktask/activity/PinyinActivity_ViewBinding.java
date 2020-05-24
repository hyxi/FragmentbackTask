// Generated code from Butter Knife. Do not modify!
package com.example.user.fragmentbacktask.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.marquee.SimpleMarqueeView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PinyinActivity_ViewBinding implements Unbinder {
  private PinyinActivity target;

  private View view7f09007e;

  @UiThread
  public PinyinActivity_ViewBinding(PinyinActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PinyinActivity_ViewBinding(final PinyinActivity target, View source) {
    this.target = target;

    View view;
    target.et_input = Utils.findRequiredViewAsType(source, R.id.et_input, "field 'et_input'", EditText.class);
    target.tv_result = Utils.findRequiredViewAsType(source, R.id.tv_result, "field 'tv_result'", TextView.class);
    target.btn_transform = Utils.findRequiredViewAsType(source, R.id.btn_transform, "field 'btn_transform'", Button.class);
    target.marquee_finance = Utils.findRequiredViewAsType(source, R.id.marquee_finance, "field 'marquee_finance'", SimpleMarqueeView.class);
    target.tv_text_one = Utils.findRequiredViewAsType(source, R.id.tv_text_one, "field 'tv_text_one'", TextView.class);
    target.tv_text_line = Utils.findRequiredViewAsType(source, R.id.tv_text_line, "field 'tv_text_line'", TextView.class);
    target.show_dialog = Utils.findRequiredViewAsType(source, R.id.show_dialog, "field 'show_dialog'", Button.class);
    view = Utils.findRequiredView(source, R.id.btn_new, "field 'btn_new' and method 'onClick'");
    target.btn_new = Utils.castView(view, R.id.btn_new, "field 'btn_new'", Button.class);
    view7f09007e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.icon = Utils.findRequiredViewAsType(source, R.id.iv_icon, "field 'icon'", ImageView.class);
    target.btnFull = Utils.findRequiredViewAsType(source, R.id.btn_fullscreen, "field 'btnFull'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PinyinActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.et_input = null;
    target.tv_result = null;
    target.btn_transform = null;
    target.marquee_finance = null;
    target.tv_text_one = null;
    target.tv_text_line = null;
    target.show_dialog = null;
    target.btn_new = null;
    target.icon = null;
    target.btnFull = null;

    view7f09007e.setOnClickListener(null);
    view7f09007e = null;
  }
}
