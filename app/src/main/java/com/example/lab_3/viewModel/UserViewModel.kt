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












