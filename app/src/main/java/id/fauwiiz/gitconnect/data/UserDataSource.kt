package id.fauwiiz.gitconnect.data

import id.fauwiiz.gitconnect.data.remote.ApiResponse
import id.fauwiiz.gitconnect.data.remote.response.DetailUserResponse
import id.fauwiiz.gitconnect.data.remote.response.SearchResponse
import id.fauwiiz.gitconnect.data.remote.response.User

interface UserDataSource {
    suspend fun getSearchUser(username: String) : ApiResponse<SearchResponse>
    suspend fun getDetailUser(username: String) : ApiResponse<DetailUserResponse>
    suspend fun getUserFollowers(username: String) : ApiResponse<ArrayList<User>>
    suspend fun getUserFollowing(username: String) : ApiResponse<ArrayList<User>>
}