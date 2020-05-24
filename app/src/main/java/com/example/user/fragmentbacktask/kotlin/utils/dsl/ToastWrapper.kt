package com.example.user.fragmentbacktask.kotlin.utils.dsl

import android.graphics.Color
import android.widget.Toast
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.kotlin.utils.ext.withErrorIcon
import com.example.user.fragmentbacktask.kotlin.utils.ext.withSuccessIcon

class ToastWrapper {

    var text:String? = null

    var res:Int? = null

    var showSuccess:Boolean = false

    var showError:Boolean = false

    var background: Int = R.drawable.shape_toast_bg

    var messageColor: Int = Color.WHITE
}

fun toast(init: ToastWrapper.() -> Unit) {
    val wrap = ToastWrapper()

    wrap.init()

    execute(wrap)
}

private fun execute(wrap: ToastWrapper) {

    var toast: Toast?=null

    wrap.text?.let {
        toast = com.example.user.fragmentbacktask.kotlin.utils.ext.toast(it)
    }

    wrap.res?.let {
        toast = com.example.user.fragmentbacktask.kotlin.utils.ext.toast(it)
    }

    if (wrap.showSuccess) {
        toast?.withSuccessIcon()
    } else if (wrap.showError) {
        toast?.withErrorIcon()
    }

//    taost?.setBackground(wrap.messageColor, wrap.background)

    toast?.show()
}