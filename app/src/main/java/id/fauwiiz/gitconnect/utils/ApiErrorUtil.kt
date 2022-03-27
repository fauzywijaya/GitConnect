package id.fauwiiz.gitconnect.utils

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ApiErrorUtil {

    fun parserError(response: Response<*>) : ErrorMessage {
        val dataGson = GsonBuilder().create()
        val errorMessage: ErrorMessage

        try {
            errorMessage = dataGson.fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d("ERROR API", it) }
            return ErrorMessage()
        }

        return errorMessage
    }
}

data class  ErrorMessage(val message: String) {
    constructor() : this("")
}