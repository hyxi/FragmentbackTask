package com.example.user.fragmentbacktask.kotlin.utils

import android.util.TypedValue
import com.example.user.fragmentbacktask.TestApplication

object DisplayUtil {
    private val metrics = TestApplication.getContext()
            .resources
            .displayMetrics

    fun dp2px(dp: Float): Int = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics) + 0.5f).toInt();
}