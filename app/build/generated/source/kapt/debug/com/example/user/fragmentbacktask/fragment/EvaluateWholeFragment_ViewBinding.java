// Generated code from Butter Knife. Do not modify!
package com.example.user.fragmentbacktask.fragment;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.user.fragmentbacktask.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EvaluateWholeFragment_ViewBinding implements Unbinder {
  private EvaluateWholeFragment target;

  @UiThread
  public EvaluateWholeFragment_ViewBinding(EvaluateWholeFragment target, View source) {
    this.target = target;

    target.jumpTablayoutBtn = Utils.findRequiredViewAsType(source, R.id.jump_tablayout_btn, "field 'jumpTablayoutBtn'", Button.class);
    target.jumpToCustomeView = Utils.findRequiredViewAsType(source, R.id.jump_to_custome_view, "field 'jumpToCustomeView'", Button.class);
    target.btnMapView = Utils.findRequiredViewAsType(source, R.id.btn_map_view, "field 'btnMapView'", Button.class);
    target.jumpToAnimator = Utils.findRequiredViewAsType(source, R.id.jump_to_animator, "field 'jumpToAnimator'", Button.class);
    target.btnBitmap = Utils.findRequiredViewAsType(source, R.id.btn_bitmap, "field 'btnBitmap'", Button.class);
    target.btnLayoutOpt = Utils.findRequiredViewAsType(source, R.id.btn_layout_opt, "field 'btnLayoutOpt'", Button.class);
    target.btnTest = Utils.findRequiredViewAsType(source, R.id.btn_test, "field 'btnTest'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EvaluateWholeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.jumpTablayoutBtn = null;
    target.jumpToCustomeView = null;
    target.btnMapView = null;
    target.jumpToAnimator = null;
    target.btnBitmap = null;
    target.btnLayoutOpt = null;
    target.btnTest = null;
  }
}
