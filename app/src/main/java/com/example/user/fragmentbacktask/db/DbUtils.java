package com.example.user.fragmentbacktask.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by huangyx
 */
public class DbUtils extends BaseDB {

    private DBHelper dbHelper = null;
    public static final int PAGE_SIZE = 20;
    private static String DB_NAME = "TestDb.db";
    //数据库存储路径
    private static final String filePath = "/sdcard/data/data/com/TestDb.db";
    private static final String pathStr = "/sdcard/data/data/com";
    Context mContext;
    private static DbUtils mDb;

    private DbUtils(Context context) {
        dbHelper = new DBHelper(context);
        mContext = context;
//        createDB();

    }

    private static SQLiteDatabase openSqliteDataBase(Context context) {
        File dbPath = new File(filePath);
        if (dbPath.exists()) {
            return SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE);
        } else {
            //不存在先创建文件夹
            File pathDir = new File(pathStr);
            if (!pathDir.exists()) {
                if (!pathDir.mkdirs())
                    pathDir.mkdirs();
            }
            File strPath = new File(pathDir.getPath() + File.separator + "TestDb.db");
            try {
                InputStream is = context.getClassLoader().getResourceAsStream("assets/" + DB_NAME);
                FileOutputStream fos = new FileOutputStream(strPath);

                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = is.read(bytes)) > -1) {
                    fos.write(bytes, 0, length);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return openSqliteDataBase(context);
    }

    public static <T> ArrayList<T> getList(String sql,Class<T> clazz, Context context) {
        T bean = null;
        ArrayList<T> list = new ArrayList<>();
        SQLiteDatabase db = openSqliteDataBase(context);
        Cursor cursor = null;
        try {
            if(db != null) {
                cursor = db.rawQuery(sql, null);
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    int count = cursor.getColumnCount();
                    bean = clazz.newInstance();
                    for (int i = 0; i < count; i++) {
                        String name = cursor.getColumnName(i);
                        try {
                            Field f = clazz.getField(name);
                            if (f != null) {
                                f.setAccessible(true);
                                f.set(bean, cursor.getString(cursor.getColumnIndex(name)));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    list.add(bean);
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        return list;
    }

    public static synchronized DbUtils getInstance(Context context) {
        if (mDb == null) {
            mDb = new DbUtils(context);
        }
        return mDb;
    }

    @Override
    public void open() {
        if (db == null || !db.isOpen()) {
            try {
                db = dbHelper.getWritableDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
