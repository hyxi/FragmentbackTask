package com.example.user.fragmentbacktask.viewmodel.room;

import java.lang.System;

/**
 * Created by huangyuxi on 2019-08-20
 * Title:
 */
@androidx.room.Database(entities = {com.example.user.fragmentbacktask.viewmodel.room.Student.class}, version = 1)
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/example/user/fragmentbacktask/viewmodel/room/StudentDb;", "Landroidx/room/RoomDatabase;", "()V", "studentDao", "Lcom/example/user/fragmentbacktask/viewmodel/room/StudentDao;", "Companion", "app_debug"})
public abstract class StudentDb extends androidx.room.RoomDatabase {
    private static com.example.user.fragmentbacktask.viewmodel.room.StudentDb instance;
    private static final java.util.ArrayList<java.lang.String> CHEESE_DATA = null;
    public static final com.example.user.fragmentbacktask.viewmodel.room.StudentDb.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.user.fragmentbacktask.viewmodel.room.StudentDao studentDao();
    
    public StudentDb() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/user/fragmentbacktask/viewmodel/room/StudentDb$Companion;", "", "()V", "CHEESE_DATA", "Ljava/util/ArrayList;", "", "instance", "Lcom/example/user/fragmentbacktask/viewmodel/room/StudentDb;", "get", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final synchronized com.example.user.fragmentbacktask.viewmodel.room.StudentDb get(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}