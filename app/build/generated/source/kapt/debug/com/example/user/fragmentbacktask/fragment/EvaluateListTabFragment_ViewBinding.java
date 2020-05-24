// Generated code from Butter Knife. Do not modify!
package com.example.user.fragmentbacktask.fragment;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.user.fragmentbacktask.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EvaluateListTabFragment_ViewBinding implements Unbinder {
  private EvaluateListTabFragment target;

  @UiThread
  public EvaluateListTabFragment_ViewBinding(EvaluateListTabFragment target, View source) {
    this.target = target;

    target.tvTabline = Utils.findRequiredViewAsType(source, R.id.tv_tabline, "field 'tvTabline'", TextView.class);
    target.rbEvaluateLeft = Utils.findRequiredViewAsType(source, R.id.rb_evaluate_left, "field 'rbEvaluateLeft'", RadioButton.class);
    target.rbEvaluateMiddle = Utils.findRequiredViewAsType(source, R.id.rb_evaluate_middle, "field 'rbEvaluateMiddle'", RadioButton.class);
    target.rbEvaluateRight = Utils.findRequiredViewAsType(source, R.id.rb_evaluate_right, "field 'rbEvaluateRight'", RadioButton.class);
    target.rgEvaluateList = Utils.findRequiredViewAsType(source, R.id.rg_evaluate_list, "field 'rgEvaluateList'", RadioGroup.class);
    target.vpEvaluateContent = Utils.findRequiredViewAsType(source, R.id.vp_evaluate_content, "field 'vpEvaluateContent'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EvaluateListTabFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTabline = null;
    target.rbEvaluateLeft = null;
    target.rbEvaluateMiddle = null;
    target.rbEvaluateRight = null;
    target.rgEvaluateList = null;
    target.vpEvaluateContent = null;
  }
}
