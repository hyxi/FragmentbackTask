package com.example.user.fragmentbacktask.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by huangyx on 2016/9/4.
 */
public class Utils {

    private String getAppVersion(String sourceFile) {
        try {
            ZipFile zipFile = new ZipFile(sourceFile);
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                if (zipEntry.getName().equals("META-INF/MANIFEST.MF")) {
                    return toString(zipFile.getInputStream(zipEntry));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String toString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().trim() + System.lineSeparator();
    }


    /**
     * 显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void showSoftKeyBroad(Context context, EditText editText) {
        InputMethodManager mgr = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // only will trigger it if no physical keyboard is open
        mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param editText
     */
    public static void hideSoftKeyBroad(Context context, EditText editText) {
        InputMethodManager mgr = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 显示软键盘，和上面的showSoftKeyBroad方法的区别在于，如果从其他activity返回的时候需要延迟一点才能显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void showKeyBoardLater(final Context context, final EditText editText,
                                         long laterTime) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showSoftKeyBroad(context, editText);
            }
        }, laterTime);
    }


}
