package com.example.railsensus.repositori

import android.os.Message
import retrofit2.Response

sealed class ApiResult<out T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class Error(val message: String, val code: Int? = null): ApiResult<Nothing>()
    object Loading: ApiResult<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend() -> Response<T>): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            ApiResult.Success(response.body()!!)
        } else {
            ApiResult.Error(
                message = response.errorBody()?.string() ?: "Unknown error",
                code = response.code()
            )
        }
    } catch (e: Exception) {
        ApiResult.Error(message = e.message ?: "Network error")
    }
}