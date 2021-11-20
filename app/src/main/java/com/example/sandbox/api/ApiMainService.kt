package com.example.sandbox.api

import okhttp3.RequestBody
import retrofit2.http.*

interface ApiMainService {

    @Headers("Accept: application/json")
    @POST("apiPostCreateLPAccount.php")
    suspend fun register(
        @Body jsonData: RequestBody
    ): RegistrationResponse

    @GET("apiGetOpenIDToken.php")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): LoginResponse

    @PUT("account/properties/update")
    @FormUrlEncoded
    suspend fun saveAccountProperties(
        @Header("Authorization") authorization: String,
        @Field("email") email: String,
        @Field("username") username: String
    ): GenericResponse

}









