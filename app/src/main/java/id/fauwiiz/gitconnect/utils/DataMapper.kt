package id.fauwiiz.gitconnect.utils

import id.fauwiiz.gitconnect.data.local.entity.UserEntity
import id.fauwiiz.gitconnect.data.remote.response.DetailUserResponse

object DataMapper {
    fun responseToEntity(profile: DetailUserResponse): UserEntity {
        return UserEntity(
            profile.username,
            if (profile.name.isNullOrEmpty()) profile.username else profile.name,
            profile.location ?: "-",
            profile.publicRepos,
            profile.company ?: "-",
            profile.followers,
            profile.following,
            profile.avatarUrl,
        )
    }
}