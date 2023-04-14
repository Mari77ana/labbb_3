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
                    usernameValue,
        )
        }

    }
    fun clearUsername(){
        _uiState.update {
            state -> state.copy(
            username = ""
            )
        }

    }
    fun getBlog(titleValue: String, blogpostValue: String){

        _uiState.update {
                state -> state.copy(
            title = state.title + titleValue,
            blogpost = state.blogpost + blogpostValue
        )
        }
    }

    fun clearBlog(){
        _uiState.update {
            state -> state.copy(
            title = "",
            blogpost = ""
            )
        }
    }

}












