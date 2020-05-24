package com.example.user.fragmentbacktask.viewmodel

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import com.example.user.fragmentbacktask.R

class LocalServiceActivity : AppCompatActivity() {

    var isBound: Boolean = false
    var mService: LocalService? = null
    var mBinder: LocalService.MyBinder? = null

    lateinit var btnBind: Button
    lateinit var btnChange: Button
    lateinit var tvMessage: TextView

    val conn: ServiceConnection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBound = true
            mBinder = service as LocalService.MyBinder
            mService = mBinder?.service
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_service)

        btnBind = findViewById(R.id.btn_bind)
        btnChange = findViewById(R.id.btn_change)
        tvMessage = findViewById(R.id.tv_message)

        btnBind.setOnClickListener {
            bindLocalService()
        }

        btnChange.setOnClickListener {
            invoke()
        }

        findViewById<Button>(R.id.btn_finish).setOnClickListener {
            if (isBound) {
                unbindService(conn)
            }
        }
    }

    operator fun invoke() {
       val result = mBinder?.invokeMethodInMyService(2, 4)
        tvMessage.text = String.format("message %d", result)

        mService?.randomNumber
    }

    private fun bindLocalService() {
        val intent = Intent(this, LocalService::class.java)
        bindService(intent, conn, Context.BIND_AUTO_CREATE) // 自动创建service
    }

}
