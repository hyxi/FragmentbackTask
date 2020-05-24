package com.example.user.fragmentbacktask.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

import androidx.core.app.ActivityCompat;

/**
 * Created by user on 2016/5/13.
 */
public class IntentUtils {
    public void dialPhone(Context mContext, String phone, boolean isShow) {
        String action = Intent.ACTION_CALL;// 在应用中启动一次呼叫有缺陷,不能用在紧急呼叫上
        if (isShow) {
            action = Intent.ACTION_DIAL;// 显示拨号界面
        }
        if (StringUtils.isNullOrEmpty(phone)) {
            return;
        }
        Intent intent = new Intent(action, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mContext.startActivity(intent);
    }

    /**
     * 是否安装了客户端
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstall(Context context, String packageName) {
        PackageManager pckMan;
        pckMan = context.getPackageManager();
        List<PackageInfo> packs = pckMan.getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (packageName.equals(p.packageName)) {
                return true;
            }
        }
        return false;
    }

}
