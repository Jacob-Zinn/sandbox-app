package com.creators.sandbox.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.creators.sandbox.models.*

@Database(entities = [Doctor::class], version = 39)

abstract class AppDatabase: RoomDatabase() {

    abstract fun getDoctorsDao(): DoctorsDao

    companion object{
        const val DATABASE_NAME: String = "app_db"
    }


}








