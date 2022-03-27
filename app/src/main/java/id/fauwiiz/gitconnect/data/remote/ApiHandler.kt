package id.fauwiiz.gitconnect.data.remote

import id.fauwiiz.gitconnect.utils.ApiErrorUtil
import retrofit2.Response
import java.lang.Exception

object ApiHandler {
    fun <T: Any> ErrorResult(response: Response<T>) : ApiResponse.Error {
        val error = ApiErrorUtil.parserError(response)
        return ApiResponse.Error(Exception(error.message))
    }

    fun<T: Any> SuccessResult(response: Response<T>) : ApiResponse<T> {
        response.body()?.let {
            return ApiResponse.Success(it)
        } ?: return ErrorResult(response)
    }
}