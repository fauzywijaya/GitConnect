package id.fauwiiz.gitconnect.data.remote

import id.fauwiiz.gitconnect.data.remote.response.DetailUserResponse
import id.fauwiiz.gitconnect.data.remote.response.SearchResponse
import id.fauwiiz.gitconnect.data.remote.response.User
import id.fauwiiz.gitconnect.BuildConfig.GITHUB_TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token $GITHUB_TOKEN")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): Response<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $GITHUB_TOKEN")
    suspend fun getDetailUser(
        @Path("username") username: String
    ) : Response<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $GITHUB_TOKEN")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ) : Response<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $GITHUB_TOKEN")
    suspend fun getUserFollowings(
        @Path("username") username: String
    ) : Response<List<User>>


}