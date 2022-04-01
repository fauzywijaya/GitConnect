package id.fauwiiz.gitconnect.data.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_users")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "location")
    var location: String,

    @ColumnInfo(name = "public_repos")
    var publicRepos: Int,

    @ColumnInfo(name = "company")
    var company: String,

    @ColumnInfo(name = "followers")
    var followers: Int,

    @ColumnInfo(name = "followings")
    var followings: Int,

    @ColumnInfo(name="avatarUrl")
    var avatarUrl: String

) : Parcelable