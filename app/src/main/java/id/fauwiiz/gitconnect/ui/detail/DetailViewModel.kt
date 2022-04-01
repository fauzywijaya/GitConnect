package id.fauwiiz.gitconnect.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fauwiiz.gitconnect.data.UserDataSource
import id.fauwiiz.gitconnect.data.local.entity.UserEntity
import id.fauwiiz.gitconnect.data.remote.ApiResponse
import id.fauwiiz.gitconnect.data.remote.response.DetailUserResponse
import id.fauwiiz.gitconnect.data.remote.response.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: UserDataSource) : ViewModel() {
    var errorMessage : MutableLiveData<String> = MutableLiveData()
    var status: MutableLiveData<Boolean> = MutableLiveData()

    private var _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private var _detail: MutableLiveData<DetailUserResponse> = MutableLiveData()
    val detail : LiveData<DetailUserResponse> get() = _detail

    fun getDetail(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                setData(username)
                _loading.postValue(false)
            }catch (e: Exception){
                _loading.postValue(false)
                errorMessage.postValue(e.toString())
            }
        }
    }

    private var followers: MutableLiveData<List<User>> = MutableLiveData()
    val listFollower: LiveData<List<User>> get() = followers

    fun getFollowers(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                setFollow(username, "followers")
                _loading.postValue(false)
            } catch (e: Exception) {
                _loading.postValue(false)
                errorMessage.postValue(e.toString())
            }
        }
    }

    private var following: MutableLiveData<List<User>> = MutableLiveData()
    val listFollowing: LiveData<List<User>> get() = following
    fun getFollowing(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                setFollow(username, "followings")
                _loading.postValue(false)
            } catch (e: Exception) {
                _loading.postValue(false)
                errorMessage.postValue(e.toString())
            }
        }
    }

    private suspend fun setFollow(username: String, type: String) {
        when (type) {
            "followers" -> {
                when (val data = repository.getUserFollowers(username)) {
                    is ApiResponse.Error -> errorMessage.postValue(data.exception.message)
                    is ApiResponse.Success -> followers.postValue(data.data!!)
                }
            }
            "followings" -> {
                when (val data = repository.getUserFollowing(username)) {
                    is ApiResponse.Error -> errorMessage.postValue(data.exception.message)
                    is ApiResponse.Success -> following.postValue(data.data!!)
                }
            }
        }
    }


    private suspend fun setData(username: String){
        when(val data = repository.getDetailUser(username)){
            is ApiResponse.Error -> errorMessage.postValue(data.exception.message)
            is ApiResponse.Success -> _detail.postValue(data.data!!)
        }
    }

    fun insertFavorite(userEntity: UserEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(userEntity)
        }
    }

    fun deleteFavorite(query: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFavorite(query)
        }
    }

    fun search(query: String) : LiveData<UserEntity> = repository.searchFavorite(query)

    fun setStatus(state:Boolean) {
        status.value = state
    }
}
