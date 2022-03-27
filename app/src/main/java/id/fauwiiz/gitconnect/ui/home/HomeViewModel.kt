package id.fauwiiz.gitconnect.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fauwiiz.gitconnect.data.UserDataSource
import id.fauwiiz.gitconnect.data.remote.ApiResponse
import id.fauwiiz.gitconnect.data.remote.response.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: UserDataSource) : ViewModel() {
    private var _listUser : MutableLiveData<SearchResponse> = MutableLiveData()
    val listUser: LiveData<SearchResponse> get() = _listUser

    var errorMessage : MutableLiveData<String> = MutableLiveData()

    fun getSearchUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getSearchUser(username)){
                is ApiResponse.Error -> errorMessage.postValue(response.exception.message)
                is ApiResponse.Success -> _listUser.postValue(response.data!!)
            }
        }
    }

}