package id.fauwiiz.gitconnect.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @field:SerializedName("items")
    val items: ArrayList<User>
)