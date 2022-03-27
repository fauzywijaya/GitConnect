package id.fauwiiz.gitconnect.data

import id.fauwiiz.gitconnect.data.remote.ApiHandler.ErrorResult
import id.fauwiiz.gitconnect.data.remote.ApiResponse
import id.fauwiiz.gitconnect.data.remote.ApiService
import id.fauwiiz.gitconnect.data.remote.ApiHandler.SuccessResult
import id.fauwiiz.gitconnect.data.remote.response.DetailUserResponse
import id.fauwiiz.gitconnect.data.remote.response.SearchResponse
import id.fauwiiz.gitconnect.data.remote.response.User
import java.lang.Exception

class UserRepository (private val remote: ApiService) : UserDataSource {
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

    override suspend fun getUserFollowers(username: String): ApiResponse<ArrayList<User>> {
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

    override suspend fun getUserFollowing(username: String): ApiResponse<ArrayList<User>> {
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

}