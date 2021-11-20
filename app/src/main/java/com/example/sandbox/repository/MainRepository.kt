package com.example.sandbox.repository


import android.content.SharedPreferences
import com.example.sandbox.api.ApiMainService
import com.example.sandbox.persistence.UserDao
import com.example.sandbox.session.SessionManager
import com.example.sandbox.util.DataBundle
import javax.inject.Inject

class MainRepository @Inject constructor(
    val apiMainService: ApiMainService,
    val authenticatedUserDao: UserDao,
    val sessionManager: SessionManager,
    val sharedPreferences: SharedPreferences,
    private val sharedPrefsEditor: SharedPreferences.Editor

) {

    fun getData() : DataBundle<String> {

        return DataBundle.data(data = "New data from the repository")
    }

}




