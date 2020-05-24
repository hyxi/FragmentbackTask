package com.example.user.fragmentbacktask.activity.viewactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;

import androidx.appcompat.app.AppCompatActivity;

public class SlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        TextView firstView = (TextView) findViewById(R.id.tv_first);
        firstView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SlideActivity.this,"点击",Toast.LENGTH_LONG).show();
            }
        });
        firstView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.i("slide","ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("slide","ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("slide","ACTION_UP");
                        break;
                }

                return false;
            }
        });
    }
}
