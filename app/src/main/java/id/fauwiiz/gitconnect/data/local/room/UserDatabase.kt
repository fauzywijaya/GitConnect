package id.fauwiiz.gitconnect.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.fauwiiz.gitconnect.data.local.entity.UserEntity


@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun dao(): UserDao
}

