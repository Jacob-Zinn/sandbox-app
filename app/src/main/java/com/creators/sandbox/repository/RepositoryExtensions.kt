package com.nznlabs.litpromx.repository

import com.creators.sandbox.util.StateEvent
import com.creators.sandbox.util.UIComponentType
import com.creators.sandbox.util.*
import com.creators.sandbox.util.ApiResult.*
import com.creators.sandbox.util.Constants.Companion.CACHE_TIMEOUT
import com.creators.sandbox.util.Constants.Companion.NETWORK_TIMEOUT
import com.creators.sandbox.util.ErrorHandling.Companion.CACHE_ERROR_TIMEOUT
import com.creators.sandbox.util.ErrorHandling.Companion.NETWORK_ERROR_TIMEOUT
import com.creators.sandbox.util.ErrorHandling.Companion.UNKNOWN_ERROR
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */
suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ApiResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(NETWORK_TIMEOUT) {
                Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408 // timeout error code
                    GenericError(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    GenericError(
                        null,
                        UNKNOWN_ERROR
                    )
                }
            }
        }
    }
}

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): CacheResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(CACHE_TIMEOUT) {
                CacheResult.Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else -> {
                    CacheResult.GenericError(UNKNOWN_ERROR)
                }
            }
        }
    }
}

//
//fun <ViewState> buildError(
//    message: String,
//    uiComponentType: UIComponentType,
//    stateEvent: StateEvent?
//): DataState<ViewState> {
//    return DataState.error(
//        response = Response(
//            message = "${stateEvent?.errorInfo()}\n\nReason: ${message}",
//            uiComponentType = uiComponentType,
//            messageType = MessageType.Error
//        ),
//        stateEvent = stateEvent
//    )
//}

suspend fun <CacheObj> getCacheResponse(
    cacheCall: suspend () -> CacheObj?,
    dispatcher: CoroutineDispatcher
): DataBundle<CacheObj> {

    return when (val cacheResult = safeCacheCall(dispatcher) { cacheCall.invoke() }) {
        is CacheResult.GenericError -> {
            DataBundle.errorState(
                errorState = true
            )
        }
        is CacheResult.Success -> {
            if (cacheResult.value == null) {
                DataBundle.errorState(
                    errorState = true
                )
            } else {
                DataBundle.data(data = cacheResult.value)
            }
        }
    }
}

suspend fun <ApiObj> getApiResponse(
    apiCall: suspend () -> ApiObj?,
    dispatcher: CoroutineDispatcher
): DataBundle<ApiObj> {

    return when (val apiResult = safeApiCall(dispatcher) { apiCall.invoke() }) {
        is GenericError -> {
            buildError(
                apiResult.errorMessage ?: UNKNOWN_ERROR,
                UIComponentType.Dialog
            )
        }

        is NetworkError -> {
            buildError(
                ErrorHandling.NETWORK_ERROR,
                UIComponentType.Dialog
            )
        }

        is Success -> {
            if (apiResult.value == null) {
                buildError(
                    UNKNOWN_ERROR,
                    UIComponentType.Dialog
                )
            } else {
                DataBundle(data = apiResult.value)
            }
        }
    }
}

fun <Obj> buildError(
    message: String,
    uiComponentType: UIComponentType,
): DataBundle<Obj> {
    return DataBundle.errorResponse(
        response = Response(
            message = "Reason: $message",
            uiComponentType = uiComponentType,
            messageType = MessageType.Error
        )
    )
}


private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        UNKNOWN_ERROR
    }
}
