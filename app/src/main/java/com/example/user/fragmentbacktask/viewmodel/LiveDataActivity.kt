package com.example.user.fragmentbacktask.viewmodel

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.user.fragmentbacktask.R
import java.time.LocalDate
import androidx.core.app.NotificationManagerCompat
import android.graphics.drawable.Icon
import android.util.ArrayMap
import android.util.SparseArray
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import com.example.user.fragmentbacktask.db.DBManager


class LiveDataActivity : AppCompatActivity() {

    lateinit var mViewModel: ScoreViewModel
    lateinit var tvName: TextView
    lateinit var btnChange: Button
    lateinit var btnSendNotifi: Button

    lateinit var mainModel: MainViewModel

    companion object {
        const val CHANNEL_ID: String = "chat_notify"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        mViewModel = ViewModelProviders.of(this).get(ScoreViewModel::class.java)
        val nameObserver = Observer<String> {textName ->
            tvName.text = textName
        }

       val sharedPref = getSharedPreferences("shared_file", Context.MODE_PRIVATE)
        sharedPref.edit().putString("key", "test").apply()

        val map = SparseArray<String>()
        map.put(-1, "sdfs")
        map.put(-4, "fs")
        map.put(5, "fs")
        val arrayMap = ArrayMap<String, String>()
        arrayMap.put("a", "a");

        mainModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var transformData = Transformations.switchMap(mViewModel.currentName) {
            val temp = MutableLiveData<String>()
            temp
        }

        mViewModel.currentName.observe(this, nameObserver)

//        mViewModel.currentName.postValue()
        createNotificationChannel()
        tvName = findViewById(R.id.tv_name)
        btnChange = findViewById(R.id.btn_change)
        btnSendNotifi = findViewById(R.id.btn_send_notification)

        btnSendNotifi.setOnClickListener {
            sendNotification()
        }

        tvName.setText("ssss")

        btnChange.setOnClickListener {
            mViewModel.currentName.value = "John Doe"
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_one, Fragment.instantiate(this, OneFragment::class.java.name))
                .add(R.id.fl_two, Fragment.instantiate(this, TwoFragment::class.java.name))
                .commit()
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notification_test"
            val description = "channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.enableVibration(true) // 震动
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET) // 锁屏是否课件
            channel.description = description
            val notifiManager = getSystemService(NotificationManager::class.java)
            notifiManager.createNotificationChannel(channel)
        }
    }

    @TargetApi(26)
    fun sendNotification() {
        val timestamp1 = LocalDate.of(2019, 7,25).toEpochDay()
        val timestamp2 = LocalDate.of(2019, 4,25).toEpochDay()
        val timestamp3 = LocalDate.of(2019, 10,25).toEpochDay()
        val timestamp4 = LocalDate.of(2019, 8,25).toEpochDay()
        val mMe = Person.Builder()
                .setName("Me MacDonald")
                .setKey("1234567890")
                .setUri("tel:1234567890")
                .setIcon(IconCompat.createWithResource(this, R.drawable.ic_opened_folder))
                .build()
      val notification = Notification.Builder(this,CHANNEL_ID)
              .setContentTitle("group message")
              .setContentText("hello world")
              .setSmallIcon(Icon.createWithResource(this, R.mipmap.icon_share))
              .setPriority(NotificationCompat.PRIORITY_DEFAULT)
              .setStyle(Notification.MessagingStyle("Me")
                      .setConversationTitle("Team lunch")
                      .addMessage("Hi", timestamp1, "") // Pass in null for user.
                      .addMessage("What's up?", timestamp2, "Coworker")
                      .addMessage("Not much", timestamp3, "ss")
                      .addMessage("How about lunch?", timestamp4, "Coworker"))
              .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(100, notification)
    }
}
