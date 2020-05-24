package com.example.user.fragmentbacktask.kotlin.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J#\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0019\u001a\u00020\u001aH \u00a2\u0006\u0002\b\u001bJ\b\u0010\u001c\u001a\u00020\u001aH\u0016J\u001e\u0010\u001d\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J#\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001aH \u00a2\u0006\u0002\b\"J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001aH\u0016J#\u0010$\u001a\u00020\u00172\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000&2\b\u0010\'\u001a\u0004\u0018\u00010(\u00a2\u0006\u0002\u0010)R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0006R \u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006*"}, d2 = {"Lcom/example/user/fragmentbacktask/kotlin/base/BaseAdapter;", "T", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/user/fragmentbacktask/kotlin/base/BaseViewHolder;", "mContext", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getMContext", "()Landroid/content/Context;", "setMContext", "mList", "", "getMList", "()Ljava/util/List;", "setMList", "(Ljava/util/List;)V", "mOnClickListener", "Landroid/view/View$OnClickListener;", "getMOnClickListener", "()Landroid/view/View$OnClickListener;", "setMOnClickListener", "(Landroid/view/View$OnClickListener;)V", "bindItemData", "", "holder", "position", "", "bindItemData$app_debug", "getItemCount", "onBindViewHolder", "onCreateItemViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onCreateItemViewHolder$app_debug", "onCreateViewHolder", "setList", "list", "", "initList", "", "(Ljava/util/List;Ljava/lang/Boolean;)V", "app_debug"})
public abstract class BaseAdapter<T extends java.lang.Object> extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.user.fragmentbacktask.kotlin.base.BaseViewHolder<T>> {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<T> mList;
    @org.jetbrains.annotations.Nullable()
    private android.view.View.OnClickListener mOnClickListener;
    @org.jetbrains.annotations.NotNull()
    private android.content.Context mContext;
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<T> getMList() {
        return null;
    }
    
    public final void setMList(@org.jetbrains.annotations.NotNull()
    java.util.List<T> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.view.View.OnClickListener getMOnClickListener() {
        return null;
    }
    
    public final void setMOnClickListener(@org.jetbrains.annotations.Nullable()
    android.view.View.OnClickListener p0) {
    }
    
    public final void setList(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> list, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean initList) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.example.user.fragmentbacktask.kotlin.base.BaseViewHolder<T> onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.user.fragmentbacktask.kotlin.base.BaseViewHolder<T> holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.user.fragmentbacktask.kotlin.base.BaseViewHolder<T> onCreateItemViewHolder$app_debug(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType);
    
    public abstract void bindItemData$app_debug(@org.jetbrains.annotations.NotNull()
    com.example.user.fragmentbacktask.kotlin.base.BaseViewHolder<T> holder, int position);
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getMContext() {
        return null;
    }
    
    public final void setMContext(@org.jetbrains.annotations.NotNull()
    android.content.Context p0) {
    }
    
    public BaseAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context mContext) {
        super();
    }
}