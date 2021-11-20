package com.example.sandbox.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sandbox.models.AuthenticatedUser

@Dao
interface UserDao {

    @Query("SELECT * FROM account_properties WHERE email = :email")
    suspend fun searchByEmail(email: String): AuthenticatedUser?

    @Query("SELECT * FROM account_properties WHERE pk = :pk")
    suspend fun searchByPk(pk: String): AuthenticatedUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndReplace(authenticatedUser: AuthenticatedUser): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(authenticatedUser: AuthenticatedUser): Long

//    @Query("UPDATE account_properties SET email = :email, username = :username WHERE pk = :pk")
//    suspend fun updateAccountProperties(pk: Int, email: String, username: String)
}













