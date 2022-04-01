package id.fauwiiz.gitconnect.data

import androidx.lifecycle.LiveData
import id.fauwiiz.gitconnect.data.local.LocalDataSource
import id.fauwiiz.gitconnect.data.local.entity.UserEntity
import id.fauwiiz.gitconnect.data.remote.ApiHandler.ErrorResult
import id.fauwiiz.gitconnect.data.remote.ApiResponse
import id.fauwiiz.gitconnect.data.remote.ApiService
import id.fauwiiz.gitconnect.data.remote.ApiHandler.SuccessResult
import id.fauwiiz.gitconnect.data.remote.response.DetailUserResponse
import id.fauwiiz.gitconnect.data.remote.response.SearchResponse
import id.fauwiiz.gitconnect.data.remote.response.User
import java.lang.Exception

class UserRepository (private val remote: ApiService, private val local: LocalDataSource) : UserDataSource {
    override suspend fun getSearchUser(username: String): ApiResponse<SearchResponse> {
        return try {
            val response = remote.getSearchUsers(username)
            if(response.isSuccessful){
                SuccessResult(response)
            }else {
                ErrorResult(response)
            }
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun getDetailUser(username: String): ApiResponse<DetailUserResponse> {
        return try {
            val response = remote.getDetailUser(username)
            if (response.isSuccessful){
                SuccessResult(response)
            }else{
                ErrorResult(response)
            }
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun getUserFollowers(username: String): ApiResponse<List<User>> {
        return try {
            val response = remote.getUserFollowers(username)
            if (response.isSuccessful){
                SuccessResult(response)
            }else{
                ErrorResult(response)
            }
        } catch (e: Exception){
            ApiResponse.Error(e)
        }
    }

    override suspend fun getUserFollowing(username: String): ApiResponse<List<User>> {
        return try {
            val response = remote.getUserFollowings(username)
            if (response.isSuccessful) {
                SuccessResult(response)
            }else {
                ErrorResult(response)
            }
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    override fun getListUserFavorite(): LiveData<List<UserEntity>> = local.getListUserFavorite()

    override fun searchFavorite(username: String): LiveData<UserEntity> = local.searchFavorite(username)

    override suspend fun insertFavorite(userEntity: UserEntity) = local.insertFavorite(userEntity)

    override suspend fun deleteFavorite(username: String) = local.delete(username)

}