package com.example.user.fragmentbacktask.kotlin.model

import java.io.Serializable

data class FindModel(var id: Int, var name: String?, var description: String?, var showName: Boolean)

class User(var name: String, var password: String): Serializable