package com.example.sandbox.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.sandbox.api.ApiMainService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.sandbox.persistence.*
import com.example.sandbox.persistence.AppDatabase.Companion.DATABASE_NAME
import com.example.sandbox.repository.MainRepository
import com.example.sandbox.session.SessionManager
import com.example.sandbox.util.Constants
import com.example.sandbox.util.PreferenceKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun provideSharedPreferences(
        application: Application
    ): SharedPreferences {
        return application
            .getSharedPreferences(
                PreferenceKeys.APP_PREFERENCES,
                Context.MODE_PRIVATE
            )
    }

    @Singleton
    @Provides
    fun provideSharedPrefsEditor(
        sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder:  Gson): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @Singleton
    @Provides
    fun provideApiMainService(retrofitBuilder: Retrofit.Builder): ApiMainService {
        return retrofitBuilder
            .build()
            .create(ApiMainService::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(
        apiMainService: ApiMainService,
        userDao: UserDao,
        sessionManager: SessionManager,
        sharedPreferences: SharedPreferences,
        sharedPrefsEditor: SharedPreferences.Editor
    ): MainRepository {
        return MainRepository(apiMainService, userDao, sessionManager, sharedPreferences, sharedPrefsEditor)
    }

    @Singleton
    @Provides
    fun provideAccountPropertiesDao(db: AppDatabase): UserDao {
        return db.getAccountPropertiesDao()
    }

}