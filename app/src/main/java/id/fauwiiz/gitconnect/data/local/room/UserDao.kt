package id.fauwiiz.gitconnect.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.fauwiiz.gitconnect.data.local.entity.UserEntity
import id.fauwiiz.gitconnect.data.remote.response.User
import java.util.concurrent.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM tb_users ORDER BY username ASC")
    fun getListUserFavorite(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(userEntity: UserEntity)

    @Query("DELETE FROM tb_users WHERE username = :query")
    suspend fun deleteFavorite(query: String)

    @Query("SELECT * FROM tb_users WHERE username = :query")
    fun searchFavorite(query: String): LiveData<UserEntity>

}