package id.fauwiiz.gitconnect.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.fauwiiz.gitconnect.data.UserDataSource
import id.fauwiiz.gitconnect.data.UserRepository
import id.fauwiiz.gitconnect.data.local.entity.UserEntity

class FavoriteViewModel(private val repository: UserDataSource) : ViewModel(){
    val userListFavorite : LiveData<List<UserEntity>> = repository.getListUserFavorite()
}