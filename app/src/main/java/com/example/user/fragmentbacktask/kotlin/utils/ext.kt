package com.example.user.fragmentbacktask.kotlin.utils

import android.view.View

private var triggerLastTime: Long = 0
private var triggerDelay: Long = 0

@Suppress("UNCHECKED_CAST")
fun <T : View> T.clickWithTrack(eventName: String, time: Long = 600, block: (T) -> Unit)
        = this.clickWithTrigger(time) {

//    TrackAgent.currentEvent().customEvent(eventName)
    block(it as T)
}

/***
 * 点击事件的View扩展
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {

    if (clickEnable()) {
        block(it as T)
    }
}

/***
 * 带延迟过滤的点击事件View扩展
 * @param delay Long 延迟时间，默认600毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
@Suppress("UNCHECKED_CAST")
fun <T : View> T.clickWithTrigger(time: Long = 600, block: (T) -> Unit){
    triggerDelay = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}
