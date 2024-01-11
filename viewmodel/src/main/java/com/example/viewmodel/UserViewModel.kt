package com.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _userLiveData = MutableLiveData<UserModel>()

    val userLiveData: LiveData<UserModel> get() = _userLiveData

    val counterLiveData = MutableLiveData(0)

    init {
        getUser()
    }

    private fun getUser() {
        _userLiveData.value = UserModel("Samvel", "Sargsyan", "https://static.vecteezy.com/system/resources/thumbnails/002/002/403/small/man-with-beard-avatar-character-isolated-icon-free-vector.jpg")
    }

    fun changeUserName(newName: String, successCallback: () -> Unit) {
        viewModelScope.launch {
            delay(5000)
            val user: UserModel = _userLiveData.value ?: return@launch
            user.name = newName
//        _userLiveData.value = user
            _userLiveData.postValue(user)
            successCallback.invoke()
        }
    }

    fun startCounter() {
        viewModelScope.launch {
            incrementCounter(1000)
        }
    }

    private suspend fun incrementCounter(delayTimeInMillis: Long) {
        delay(delayTimeInMillis)
        var counter = counterLiveData.value ?: 0
        counter++
        counterLiveData.postValue(counter)
        incrementCounter(delayTimeInMillis + 1000)
    }
}