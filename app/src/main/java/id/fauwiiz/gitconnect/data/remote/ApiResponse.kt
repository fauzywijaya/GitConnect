package id.fauwiiz.gitconnect.data.remote

import java.lang.Exception

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T): ApiResponse<T>()

    class Error(val exception: Exception) : ApiResponse<Nothing>()
}