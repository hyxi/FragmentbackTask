package com.example.user.fragmentbacktask.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huangyx
 */
public class DBHelper extends SQLiteOpenHelper {
    // 数据库版本
    private static int DB_VERSION = 1;
    private Context mContext;

    //assets文件夹下数据库名称
    private static final String DB_NAME = "TestDb.db";
    private static String DB_PATH = "";

    public DBHelper(Context mContext) {
        super(mContext, "TestDb", null, DB_VERSION);
        this.mContext = mContext;
        DB_PATH = mContext.getApplicationInfo().dataDir + "/" + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        copyAssertDb();
    }

    // 读取assert 目录下数据库
    private void copyAssertDb() {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            InputStream inputStream = mContext.getAssets().open(DB_NAME);
            Logger.d("sqlite helper: onreate");
            bis = new BufferedInputStream(inputStream);
            fos = new FileOutputStream(DB_PATH);

            bos = new BufferedOutputStream(fos);
            byte[] data = new byte[1024];
            int len;
            while ((len = bis.read(data)) != -1) {
                bos.write(data, 0, len);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
