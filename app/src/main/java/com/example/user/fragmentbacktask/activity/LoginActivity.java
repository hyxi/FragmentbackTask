package com.example.user.fragmentbacktask.activity;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.fragmentbacktask.R;

public class LoginActivity extends Activity {

    private EditText et_name;
    private EditText et_phone;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        btn_login = (Button) findViewById(R.id.btn_login);
    }
}
