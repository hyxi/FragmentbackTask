package com.example.user.fragmentbacktask.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\'\u001a\u00020(H\u0002J\t\u0010)\u001a\u00020(H\u0086\u0002J\u0012\u0010*\u001a\u00020(2\b\u0010+\u001a\u0004\u0018\u00010,H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0012\"\u0004\b\u0013\u0010\u0014R \u0010\u0015\u001a\b\u0018\u00010\u0016R\u00020\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&\u00a8\u0006-"}, d2 = {"Lcom/example/user/fragmentbacktask/viewmodel/LocalServiceActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnBind", "Landroid/widget/Button;", "getBtnBind", "()Landroid/widget/Button;", "setBtnBind", "(Landroid/widget/Button;)V", "btnChange", "getBtnChange", "setBtnChange", "conn", "Landroid/content/ServiceConnection;", "getConn", "()Landroid/content/ServiceConnection;", "isBound", "", "()Z", "setBound", "(Z)V", "mBinder", "Lcom/example/user/fragmentbacktask/viewmodel/LocalService$MyBinder;", "Lcom/example/user/fragmentbacktask/viewmodel/LocalService;", "getMBinder", "()Lcom/example/user/fragmentbacktask/viewmodel/LocalService$MyBinder;", "setMBinder", "(Lcom/example/user/fragmentbacktask/viewmodel/LocalService$MyBinder;)V", "mService", "getMService", "()Lcom/example/user/fragmentbacktask/viewmodel/LocalService;", "setMService", "(Lcom/example/user/fragmentbacktask/viewmodel/LocalService;)V", "tvMessage", "Landroid/widget/TextView;", "getTvMessage", "()Landroid/widget/TextView;", "setTvMessage", "(Landroid/widget/TextView;)V", "bindLocalService", "", "invoke", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class LocalServiceActivity extends androidx.appcompat.app.AppCompatActivity {
    private boolean isBound;
    @org.jetbrains.annotations.Nullable()
    private com.example.user.fragmentbacktask.viewmodel.LocalService mService;
    @org.jetbrains.annotations.Nullable()
    private com.example.user.fragmentbacktask.viewmodel.LocalService.MyBinder mBinder;
    @org.jetbrains.annotations.NotNull()
    public android.widget.Button btnBind;
    @org.jetbrains.annotations.NotNull()
    public android.widget.Button btnChange;
    @org.jetbrains.annotations.NotNull()
    public android.widget.TextView tvMessage;
    @org.jetbrains.annotations.NotNull()
    private final android.content.ServiceConnection conn = null;
    private java.util.HashMap _$_findViewCache;
    
    public final boolean isBound() {
        return false;
    }
    
    public final void setBound(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.user.fragmentbacktask.viewmodel.LocalService getMService() {
        return null;
    }
    
    public final void setMService(@org.jetbrains.annotations.Nullable()
    com.example.user.fragmentbacktask.viewmodel.LocalService p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.user.fragmentbacktask.viewmodel.LocalService.MyBinder getMBinder() {
        return null;
    }
    
    public final void setMBinder(@org.jetbrains.annotations.Nullable()
    com.example.user.fragmentbacktask.viewmodel.LocalService.MyBinder p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.Button getBtnBind() {
        return null;
    }
    
    public final void setBtnBind(@org.jetbrains.annotations.NotNull()
    android.widget.Button p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.Button getBtnChange() {
        return null;
    }
    
    public final void setBtnChange(@org.jetbrains.annotations.NotNull()
    android.widget.Button p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.TextView getTvMessage() {
        return null;
    }
    
    public final void setTvMessage(@org.jetbrains.annotations.NotNull()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.ServiceConnection getConn() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void invoke() {
    }
    
    private final void bindLocalService() {
    }
    
    public LocalServiceActivity() {
        super();
    }
}