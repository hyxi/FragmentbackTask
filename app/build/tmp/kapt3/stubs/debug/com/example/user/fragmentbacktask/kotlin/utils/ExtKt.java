package com.example.user.fragmentbacktask.kotlin.utils;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 2, d1 = {"\u0000.\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u001a-\u0010\u0003\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u0006*\u0002H\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00040\b\u00a2\u0006\u0002\u0010\t\u001a\u001b\u0010\n\u001a\u00020\u000b\"\b\b\u0000\u0010\u0005*\u00020\u0006*\u0002H\u0005H\u0002\u00a2\u0006\u0002\u0010\f\u001a?\u0010\r\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u0006*\u0002H\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00040\b\u00a2\u0006\u0002\u0010\u0011\u001a7\u0010\u0012\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u0006*\u0002H\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00040\b\u00a2\u0006\u0002\u0010\u0013\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"triggerDelay", "", "triggerLastTime", "click", "", "T", "Landroid/view/View;", "block", "Lkotlin/Function1;", "(Landroid/view/View;Lkotlin/jvm/functions/Function1;)V", "clickEnable", "", "(Landroid/view/View;)Z", "clickWithTrack", "eventName", "", "time", "(Landroid/view/View;Ljava/lang/String;JLkotlin/jvm/functions/Function1;)V", "clickWithTrigger", "(Landroid/view/View;JLkotlin/jvm/functions/Function1;)V", "app_debug"})
public final class ExtKt {
    private static long triggerLastTime;
    private static long triggerDelay;
    
    @kotlin.Suppress(names = {"UNCHECKED_CAST"})
    public static final <T extends android.view.View>void clickWithTrack(@org.jetbrains.annotations.NotNull()
    T $this$clickWithTrack, @org.jetbrains.annotations.NotNull()
    java.lang.String eventName, long time, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> block) {
    }
    
    /**
     * *
     * 点击事件的View扩展
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    @kotlin.Suppress(names = {"UNCHECKED_CAST"})
    public static final <T extends android.view.View>void click(@org.jetbrains.annotations.NotNull()
    T $this$click, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> block) {
    }
    
    /**
     * *
     * 带延迟过滤的点击事件View扩展
     * @param delay Long 延迟时间，默认600毫秒
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    @kotlin.Suppress(names = {"UNCHECKED_CAST"})
    public static final <T extends android.view.View>void clickWithTrigger(@org.jetbrains.annotations.NotNull()
    T $this$clickWithTrigger, long time, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> block) {
    }
    
    private static final <T extends android.view.View>boolean clickEnable(@org.jetbrains.annotations.NotNull()
    T $this$clickEnable) {
        return false;
    }
}