package com.example.user.fragmentbacktask.viewmodel.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by huangyuxi on 2019-08-20
 * Title:
 */
@Entity
data class Student(@PrimaryKey(autoGenerate = true) val id: Int, @ColumnInfo(name ="sname") val name: String)