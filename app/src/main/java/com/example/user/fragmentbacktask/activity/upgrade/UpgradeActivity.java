package com.example.user.fragmentbacktask.activity.upgrade;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.user.fragmentbacktask.view.GeneralDialog;

public class UpgradeActivity extends Activity {

    private GeneralDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handlerIntent(getIntent());
    }

    private void handlerIntent(Intent intent){
        GeneralDialog.Builder builder = new GeneralDialog.Builder(this);
        builder.setMessage("打开无viewactivity");
        builder.setTitle("新activity");

        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                doUpdate();
            }
        });
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void doUpdate(){
        Log.i("tag","开始更新");
    }

}
