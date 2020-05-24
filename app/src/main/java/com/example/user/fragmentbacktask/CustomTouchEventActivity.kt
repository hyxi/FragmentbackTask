package com.example.user.fragmentbacktask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import com.orhanobut.logger.Logger

class CustomTouchEventActivity : AppCompatActivity() {

    private lateinit var btn_back: Button

    companion object {
        val TAG = "activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_touch_event)

        btn_back = findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.getAction()) {
            MotionEvent.ACTION_DOWN -> Logger.d("$TAG: dispatchTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_UP -> Logger.d("$TAG: dispatchTouchEvent ACTION_UP")
            MotionEvent.ACTION_MOVE -> Logger.d("$TAG: dispatchTouchEvent ACTION_MOVE")
        }

        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.getAction()) {
            MotionEvent.ACTION_DOWN -> Logger.d("$TAG: onTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_UP -> Logger.d("$TAG: onTouchEvent ACTION_UP")
            MotionEvent.ACTION_MOVE -> Logger.d("$TAG: onTouchEvent ACTION_MOVE")
        }
        return super.onTouchEvent(event)
    }
}
