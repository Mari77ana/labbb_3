package com.example.lab_3.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    //private val _username = MutableStateFlow("")
    //val username: StateFlow<String> = _username

    /*
    fun setUsername(newUsername: String) {
        _username.value = newUsername
    }

     */

    fun getUsername(usernameValue: String) {
        _uiState.update {
                state -> state.copy(
            username = state.username +
                    usernameValue
        )
        }

    }

}

/*
    fun getUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

 */

/*
    fun getUsername(usernameValue: String) {
        Log.d(TAG, "getUsername called with value: $usernameValue")
        _uiState.update { state ->
            state.copy(username = state.username + usernameValue)
        }
        Log.d(TAG, "uiState updated with value: ${_uiState.value}")
    }

 */

/*
    fun getUsername(usernameValue: String) {
        _uiState.value = UserUiState(usernameValue)
    }

 */

/*

    private val _userUiState = MutableLiveData<UserUiState>()

    val userUiState: LiveData<UserUiState>
    get() = _userUiState

    fun getUsername(username: String){
        _userUiState.value = UserUiState(username)
    }

     */





