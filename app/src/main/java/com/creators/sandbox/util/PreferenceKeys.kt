package com.creators.sandbox.util

class PreferenceKeys {

    companion object{

        // Shared Preference Files:
        const val APP_PREFERENCES: String = "com.creators.sandbox.APP_PREFERENCES"

        // Authenticated user
        const val AUTH_USER_TOKEN: String = "com.nznlabs.litpro_mx.AUTH_USER_TOKEN"
        const val AUTH_USER_EMAIL: String = "com.nznlabs.litpro_mx.AUTH_USER_EMAIL"
        const val AUTH_USER_IDENTITY_ID: String = "com.nznlabs.litpro_mx.AUTH_USER_IDENTITY_ID"
        const val AUTH_USER_FIRST_NAME: String = "com.nznlabs.litpro_mx.AUTH_USER_FIRST_NAME"
        const val AUTH_USER_LAST_NAME: String = "com.nznlabs.litpro_mx.AUTH_USER_LAST_NAME"
        const val AUTH_USER_SUB_LEVEL: String = "com.nznlabs.litpro_mx.AUTH_USER_SUB_LEVEL"

        // Notification endpoint
        const val PUSH_TOKEN_ENDPOINT_ARN: String = "com.nznlabs.litpro_mx.PUSH_TOKEN_ENDPOINT_ARN"
        const val CURRENT_PUSH_TOKEN: String = "com.nznlabs.litpro_mx.CURRENT_PUSH_TOKEN"

    }
}