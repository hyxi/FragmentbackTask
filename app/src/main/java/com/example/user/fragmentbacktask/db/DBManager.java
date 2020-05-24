package com.example.user.fragmentbacktask.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.fragmentbacktask.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hyxi
 */
public class DBManager extends BaseDB {

    private DBHelper dbHelper = null;
    private DBManager mDb;
    private Context mContext;

    private AtomicInteger mOpenCounter = new AtomicInteger();

    public DBManager(Context context) {
        mContext = context;
        dbHelper = new DBHelper(context);
    }

    @Override
    public void open() {
        if (db == null || !db.isOpen()) {
            try {
                if (mOpenCounter.incrementAndGet() == 1) {
                    db = dbHelper.getWritableDatabase();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Opening new database
    private synchronized SQLiteDatabase getWritableDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }

    // Opening new database
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            db = dbHelper.getReadableDatabase();
        }
        return db;
    }

    // Closing database
    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            db.close();
        }
    }

}
