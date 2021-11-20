package com.example.sandbox.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse(

    @SerializedName("identityId")
    @Expose
    var pk: String,

    @Expose
    var token: String,

    @SerializedName("username")
    @Expose
    var email: String,

    @Expose
    var firstName: String,

    @Expose
    var lastName: String,

    @Expose
    var subLevel: String,

    @Expose
    var error: String
)
{
    override fun toString(): String {
        return "LoginResponse(identityId(pk)='$pk', token='$token', email='$email', firstname=$firstName, lastname='$lastName', subLevel='$subLevel')"
    }
}
