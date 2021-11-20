package com.creators.sandbox.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegistrationResponse(

    @SerializedName("response")
    @Expose
    var response: String,

    @SerializedName("error_message")
    @Expose
    var errorMessage: String)
{
    override fun toString(): String {
        return "RegistrationResponse(response='$response', errorMessage='$errorMessage')"
    }
}