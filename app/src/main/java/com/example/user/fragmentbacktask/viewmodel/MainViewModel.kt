package com.example.user.fragmentbacktask.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.user.fragmentbacktask.viewmodel.room.Student
import com.example.user.fragmentbacktask.viewmodel.room.StudentDb
import com.orhanobut.logger.Logger

/**
 * Created by huangyuxi on 2019-08-20
 * Title:
 */
class MainViewModel(app: Application): AndroidViewModel(app) {

    init {
        Logger.d("mainmodel init")
        val dao = StudentDb.get(app).studentDao()

        Thread{
//            dao.insert(Student(1000, "张三"))
            val students = dao.students

            students.forEach {
                Logger.d("student: ${it.name}")
            }

        }.start()


    }


}