package com.creators.sandbox.util

data class DataBundle<T>(
    var errorState: Boolean? = null,
    var errorResponse: Message? = null,
    var data: T? = null,
) {

    companion object {

        fun <T> errorState(
            errorState: Boolean
        ): DataBundle<T> {
            return DataBundle(
                errorState = errorState,
            )
        }

        fun <T> errorResponse(
            response: Response
        ): DataBundle<T> {
            return DataBundle(
                errorResponse = Message(response)
            )
        }

        fun <T> data(
            data: T? = null
        ): DataBundle<T> {
            return DataBundle(
                data = data
            )
        }
    }
}