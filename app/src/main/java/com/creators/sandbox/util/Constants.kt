package com.creators.sandbox.util

class Constants {

    companion object{

        const val BASE_URL = "http://10.0.2.2:8080/"
        const val APP_NAME: String = "Android MX"

        const val NETWORK_TIMEOUT = 30000L
        const val CACHE_TIMEOUT = 3000L
//        const val TESTING_NETWORK_DELAY = 0L // fake network delay for testing
//        const val TESTING_CACHE_DELAY = 0L // fake cache delay for testing

        const val PERMISSIONS_REQUEST_READ_STORAGE: Int = 301
        const val SUCCESS: Int = 200

        // OTHER
        const val WILDCARD_OPERATOR = "%"

    }

    // Notifcation channels
    enum class NotificationChannels(val title: String) {
        NOTIFICATION_NEW_SESSION_AVAILABLE("New session is available"),
        NOTIFICATION_CHALLENGE_UPDATE("LITPro Challenge update")
    }
}