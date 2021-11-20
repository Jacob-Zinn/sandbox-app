package com.creators.sandbox.util

class ErrorHandling{

    companion object{

        const val ERROR_SAVE_ACCOUNT_PROPERTIES = -1
        const val ERROR_SAVE_AUTH_TOKEN = "Error saving authentication token.\nTry restarting the app."

        const val GENERIC_AUTH_ERROR = "not authorized"
        private const val INVALID_PAGE = "Invalid page."
        const val INVALID_CREDENTIALS = "Invalid credentials"
        const val INVALID_STATE_EVENT = "Invalid state event"
        const val NETWORK_ERROR = "Network error"
        const val NETWORK_ERROR_TIMEOUT = "Network timeout"
        const val CACHE_ERROR_TIMEOUT = "Cache timeout"
        const val UNKNOWN_ERROR = "Unknown error"
        const val MUST_SELECT_IMAGE = "You must select an image."
        const val PLEASE_ENABLE_CAMERA_ACCESS = "In order to change your profile picture, please enable access to your camera."

        fun isPaginationDone(errorResponse: String?): Boolean{
            // if error response = '{"detail":"Invalid page."}' then pagination is finished
            return errorResponse?.contains(INVALID_PAGE)?: false
        }
    }

}