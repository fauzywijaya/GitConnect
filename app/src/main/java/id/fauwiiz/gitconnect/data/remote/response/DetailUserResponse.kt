package id.fauwiiz.gitconnect.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("login")
    val username: String,
    @field:SerializedName("location")
    val location: String?,
    @field:SerializedName("public_repos")
    val publicRepos: String,
    @field:SerializedName("company")
    val company: String?,
    @field:SerializedName("followers")
    val followers: Int,
    @field:SerializedName("following")
    val following: Int,
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    @field:SerializedName("html_url")
    val htmlUrl: String,
    )