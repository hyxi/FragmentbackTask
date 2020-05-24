package com.example.user.fragmentbacktask.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.fragmentbacktask.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huangyx
 */
public abstract class BaseDB {

    protected SQLiteDatabase db = null;

    public abstract void open();

    public void close() {
        try {
            if (db != null) {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor RawQuery(String sql, String tableName) {
        open();
        return db.rawQuery(sql, null);
    }

    /**
     * 添加数据 入库
     *
     * @param obj       实体
     * @param tableName 表名
     */
    public void add(final Object obj, final String tableName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                open();
                ContentValues values = new ContentValues();
                Cursor cursor = null;
                try {
                    cursor = RawQuery("SELECT * FROM " + tableName + " ", tableName);
                    int count = cursor.getColumnCount();
                    for (int i = 0; i < count; i++) {
                        String name = cursor.getColumnName(i);
                        try {
                            Field f = obj.getClass().getField(name);
                            if (!"_id".equals(name) || !"id".equals(name)) {
                                String value = (String) f.get(obj);
                                if (!StringUtils.isNullOrEmpty(value)) {
                                    values.put(name, value);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (tableName.length() > 0) {
                        db.insert(tableName, "", values);
                    }
                } catch (Exception e) {
                    //System.out.println(e.getMessage());
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }).start();

    }

    /**
     * 批量添加数据 入库
     *
     * @param <T>       实体类泛型
     * @param tableName 表名
     */
    public <T> void addList(List<T> list, String tableName) {
        Cursor cursor = null;
        try {
            open();
            List<String> li = new ArrayList<String>();
            cursor = RawQuery("SELECT * FROM " + tableName + " ", tableName);
            int count = cursor.getColumnCount();
            for (int i = 0; i < count; i++) {
                String name = cursor.getColumnName(i);
                li.add(name);
            }
            ExecutorService exec = Executors.newFixedThreadPool(3);
            for (Object obj : list) {
                exec.execute(new addRunnable(obj, li, tableName));
            }
            exec.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
    }

    /**
     * 查询表中数据的条数
     *
     * @param tableName 表名
     * @param where     查询条件
     */
    public long getCount(String tableName, String where) {
        open();
        if (StringUtils.isNullOrEmpty(where)) {
            where = "";
        } else if (!where.contains("where")) {
            where = "where " + where;
        }
        Cursor cursor = null;
        try {
            cursor = RawQuery("select count(*) from " + tableName + " " + where, tableName);
            cursor.moveToFirst();
            return cursor.getLong(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }

    public class addRunnable implements Runnable {
        Object obj;
        List<String> list;
        String tableName;

        public addRunnable(Object obj, List<String> list, String tableName) {
            this.obj = obj;
            this.list = list;
            this.tableName = tableName;
        }

        @Override
        public void run() {
            open();
            ContentValues values = new ContentValues();
            for (String name : list) {
                try {
                    Field f = obj.getClass().getField(name);
                    if (!"_id".equalsIgnoreCase(name) || !"id".equalsIgnoreCase(name)) {
                        String value = (String) f.get(obj);
                        if (!StringUtils.isNullOrEmpty(value)) {
                            values.put(name, value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            db.insert(tableName, "", values);
        }
    }


}
