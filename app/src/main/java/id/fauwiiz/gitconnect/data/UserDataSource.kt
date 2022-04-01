package id.fauwiiz.gitconnect.data

import androidx.lifecycle.LiveData
import id.fauwiiz.gitconnect.data.local.entity.UserEntity
import id.fauwiiz.gitconnect.data.remote.ApiResponse
import id.fauwiiz.gitconnect.data.remote.response.DetailUserResponse
import id.fauwiiz.gitconnect.data.remote.response.SearchResponse
import id.fauwiiz.gitconnect.data.remote.response.User

interface UserDataSource {

    // Remote Data Source
    suspend fun getSearchUser(username: String) : ApiResponse<SearchResponse>
    suspend fun getDetailUser(username: String) : ApiResponse<DetailUserResponse>
    suspend fun getUserFollowers(username: String) : ApiResponse<List<User>>
    suspend fun getUserFollowing(username: String) : ApiResponse<List<User>>

    // Local Data Source
    fun getListUserFavorite() : LiveData<List<UserEntity>>
    fun searchFavorite(username: String) :  LiveData<UserEntity>
    suspend fun insertFavorite(userEntity: UserEntity)
    suspend fun deleteFavorite(username: String)
}