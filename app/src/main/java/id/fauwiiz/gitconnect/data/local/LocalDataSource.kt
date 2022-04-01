package id.fauwiiz.gitconnect.data.local

import androidx.lifecycle.LiveData
import id.fauwiiz.gitconnect.data.local.entity.UserEntity
import id.fauwiiz.gitconnect.data.local.room.UserDao

class LocalDataSource(private val dao: UserDao){
    fun getListUserFavorite() : LiveData<List<UserEntity>> = dao.getListUserFavorite()
    fun searchFavorite(username: String) : LiveData<UserEntity> = dao.searchFavorite(username)

    suspend fun insertFavorite(userEntity: UserEntity) = dao.insertFavorite(userEntity)
    suspend fun delete(username: String) = dao.deleteFavorite(username)

}