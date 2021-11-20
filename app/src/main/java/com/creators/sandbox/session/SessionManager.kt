package com.creators.sandbox.session

import android.app.Application
import android.content.SharedPreferences
import com.creators.sandbox.api.ApiMainService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val sharedPrefsEditor: SharedPreferences.Editor,
    private val apiMainService: ApiMainService,
    val application: Application
) {


}
