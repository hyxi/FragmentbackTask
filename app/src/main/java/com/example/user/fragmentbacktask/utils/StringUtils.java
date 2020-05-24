package com.example.user.fragmentbacktask.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.TestApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * Created by user on 2016/5/11.
 */
public class StringUtils {

    public static int dp2px(float dpValue) {
        final float scale = TestApplication.getSelf().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean isNullOrEmpty(String text) {
        if (text == null || "".equals(text.trim()) || text.trim().length() == 0
                || "null".equals(text.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static void setParagraphSpacing(Context context, TextView tv, String content, int paragraphSpacing, int lineSpacingExtra) {
        if (!content.contains("\n")) {
            tv.setText(content);
            return;
        }
        content = content.replace("\n", "\n\r");

        int previousIndex = content.indexOf("\n\r");
        //记录每个段落开始的index，第一段没有，从第二段开始
        List<Integer> nextParagraphBeginIndexes = new ArrayList<>();
        nextParagraphBeginIndexes.add(previousIndex);
        while (previousIndex != -1) {
            previousIndex = content.indexOf("\n\r", previousIndex + 2);
            if (previousIndex != -1) {
                nextParagraphBeginIndexes.add(previousIndex);
            }
        }
        float lineHeight = tv.getLineHeight();
        //把\r替换成透明长方形（宽:1px，高：字高+段距）
        SpannableString spanString = new SpannableString(content);
        Drawable transparentDrawable = ContextCompat.getDrawable(context, R.drawable.transparent);
        float density = context.getResources().getDisplayMetrics().density;
        //int强转部分为：行高 - 行距 + 段距
        int bottom = (int) ((lineHeight - lineSpacingExtra * density) / 1.2 + (paragraphSpacing - lineSpacingExtra) * density);
        transparentDrawable.setBounds(0, 0, 1, bottom);

        for (int index : nextParagraphBeginIndexes) {
            spanString.setSpan(new ImageSpan(transparentDrawable), index + 1, index + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanString);
    }

    /**
     * 对流转化成字符串
     *
     * @param is
     * @return
     */
    public static String getStringByStream(InputStream is) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line + "\n");
            }
            return buffer.toString().replaceAll("\n\n", "\n");
        } catch (OutOfMemoryError o) {
            System.gc();
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 获得状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static void toast(Context context,String message){
        Context mContext = context.getApplicationContext();
        Toast.makeText(mContext,message, Toast.LENGTH_LONG).show();
    }

}
