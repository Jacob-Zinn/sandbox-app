package com.example.sandbox.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sandbox.models.*

@Database(entities = [AuthenticatedUser::class], version = 36)

abstract class AppDatabase: RoomDatabase() {

    abstract fun getAccountPropertiesDao(): UserDao

    companion object{
        const val DATABASE_NAME: String = "app_db"
    }


}








