package com.example.user.fragmentbacktask.kotlin.utils.ext

import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.TestApplication
import com.example.user.fragmentbacktask.kotlin.utils.DisplayUtil


fun Toast.setGravityCenter(): Toast {
    setGravity(Gravity.CENTER, 0, 0)
    return this
}

fun Toast.setGravity(gravity: Int): Toast{
    setGravity(gravity, 0, 0)
    return this
}

fun Toast.setToastColor(@ColorInt messageColor: Int, @ColorInt backgroundColor: Int) {
    val view = view
    if (view != null) {
        val message = view.findViewById(android.R.id.message) as TextView
        message.setBackgroundColor(backgroundColor)
        message.setTextColor(messageColor)
    }
}

fun Toast.setBackground(@ColorInt messageColor: Int = Color.BLACK,
                        @DrawableRes background: Int = R.drawable.shape_toast_bg): Toast {
    val view = view
    if (view != null) {
        val message = view.findViewById(android.R.id.message) as TextView
        view.setBackgroundResource(background)
        message.setTextColor(messageColor)
    }
    return this
}

//@SuppressLint("ShowToast")
fun toast(text: CharSequence): Toast {
    // 自定义toast
    var cusToast = Toast(TestApplication.mContext)
    val rootView = LinearLayout(TestApplication.mContext)
    cusToast.view = rootView
    val toast = Toast.makeText(TestApplication.mContext, text, Toast.LENGTH_SHORT)
   return  toast.setGravityCenter()
            .setBackground()
}

//需要的地方调用withErrorIcon，默认不要添加
//        .withErrorIcon()
//


//@SuppressLint("ShowToast")
fun toast(@StringRes res: Int): Toast = Toast.makeText(TestApplication.getSelf(),
        TestApplication.getSelf().resources.getString(res), Toast.LENGTH_LONG)
        .setGravityCenter()
        .setBackground()
//需要的地方调用withErrorIcon，默认不要添加
//        .withErrorIcon()
//


fun Toast.withErrorIcon(@DrawableRes iconRes: Int = R.mipmap.ic_launcher): Toast {
    val view = view
    if (view != null) {
        val layout = this.view as LinearLayout
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val icon = ImageView(TestApplication.mContext)
        icon.setImageResource(iconRes)
        icon.setPadding(0, 0, DisplayUtil.dp2px(8f), 0)
        icon.layoutParams = layoutParams
        layout.orientation = LinearLayout.HORIZONTAL
        layout.gravity = Gravity.CENTER_VERTICAL
        layout.addView(icon, 0)
    }
    return this
}

fun Toast.withSuccessIcon(@DrawableRes iconRes: Int = R.mipmap.ic_launcher): Toast {
    val view = view
    if (view != null) {
        val layout = this.view as LinearLayout
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val icon = ImageView(TestApplication.mContext)
        icon.setImageResource(iconRes)
        icon.setPadding(DisplayUtil.dp2px(8f), DisplayUtil.dp2px(8f),
                DisplayUtil.dp2px(8f), DisplayUtil.dp2px(8f))
        icon.layoutParams = layoutParams
        layout.orientation = LinearLayout.HORIZONTAL
        layout.gravity = Gravity.CENTER_VERTICAL
        layout.addView(icon, 0)
    }
    return this
}
