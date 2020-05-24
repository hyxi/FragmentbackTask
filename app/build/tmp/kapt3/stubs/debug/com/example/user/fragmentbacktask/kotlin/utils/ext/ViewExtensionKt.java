package com.example.user.fragmentbacktask.kotlin.utils.ext;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 2, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a-\u0010\u0011\u001a\u00020\u0012\"\b\b\u0000\u0010\t*\u00020\u0002*\u0002H\t2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00120\u0014\u00a2\u0006\u0002\u0010\u0015\u001a\u001b\u0010\u0016\u001a\u00020\u0001\"\b\b\u0000\u0010\t*\u00020\u0002*\u0002H\tH\u0002\u00a2\u0006\u0002\u0010\u0003\u001a\n\u0010\u0017\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0018\u001a\u00020\u0001*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003\"2\u0010\b\u001a\u00020\u0007\"\b\b\u0000\u0010\t*\u00020\u0002*\u0002H\t2\u0006\u0010\u0006\u001a\u00020\u00078B@BX\u0082\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\"2\u0010\u000e\u001a\u00020\u0007\"\b\b\u0000\u0010\t*\u00020\u0002*\u0002H\t2\u0006\u0010\u0006\u001a\u00020\u00078B@BX\u0082\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\r\u00a8\u0006\u0019"}, d2 = {"isGone", "", "Landroid/view/View;", "(Landroid/view/View;)Z", "isInvisible", "isVisible", "value", "", "triggerDelay", "T", "getTriggerDelay", "(Landroid/view/View;)J", "setTriggerDelay", "(Landroid/view/View;J)V", "triggerLastTime", "getTriggerLastTime", "setTriggerLastTime", "click", "", "block", "Lkotlin/Function1;", "(Landroid/view/View;Lkotlin/jvm/functions/Function1;)V", "clickEnable", "hideKeyboard", "showKeyboard", "app_debug"})
public final class ViewExtensionKt {
    
    private static final <T extends android.view.View>long getTriggerLastTime(@org.jetbrains.annotations.NotNull()
    T $this$triggerLastTime) {
        return 0L;
    }
    
    private static final <T extends android.view.View>void setTriggerLastTime(@org.jetbrains.annotations.NotNull()
    T $this$triggerLastTime, long value) {
    }
    
    private static final <T extends android.view.View>long getTriggerDelay(@org.jetbrains.annotations.NotNull()
    T $this$triggerDelay) {
        return 0L;
    }
    
    private static final <T extends android.view.View>void setTriggerDelay(@org.jetbrains.annotations.NotNull()
    T $this$triggerDelay, long value) {
    }
    
    private static final <T extends android.view.View>boolean clickEnable(@org.jetbrains.annotations.NotNull()
    T $this$clickEnable) {
        return false;
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
    
    public static final boolean isVisible(@org.jetbrains.annotations.NotNull()
    android.view.View $this$isVisible) {
        return false;
    }
    
    public static final boolean isInvisible(@org.jetbrains.annotations.NotNull()
    android.view.View $this$isInvisible) {
        return false;
    }
    
    public static final boolean isGone(@org.jetbrains.annotations.NotNull()
    android.view.View $this$isGone) {
        return false;
    }
    
    public static final boolean hideKeyboard(@org.jetbrains.annotations.NotNull()
    android.view.View $this$hideKeyboard) {
        return false;
    }
    
    public static final boolean showKeyboard(@org.jetbrains.annotations.NotNull()
    android.view.View $this$showKeyboard) {
        return false;
    }
}