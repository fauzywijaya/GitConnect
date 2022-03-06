package id.fauwiiz.gitconnect.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String,
    var username: String,
    var location: String,
    var repository: String,
    var company: String,
    var followers: String,
    var following: String,
    var avatar: Int
) : Parcelable