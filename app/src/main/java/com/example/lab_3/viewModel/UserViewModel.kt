package com.example.lab_3.viewModel

import androidx.lifecycle.ViewModel
import com.example.lab_3.Blog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    // Befintliga användaren
    fun setCurrentUser(username: String, title: String, blogpost: String, id: String, blogList: ArrayList<Blog>?) {
        _uiState.update {
            state -> state.copy(
            username = username,
            password = state.password, // behåller samma lösenord
            //username = state.username + username,
            //title = state.title + title,
            title = title,
            blogpost = blogpost,
            blogList = state.blogList?.plus((Blog(title,blogpost)))
            //blogList = state.blogList hade den innan
            //state.blogList?.plus((Blog(title,blogpost))) ?: blogList, // uppdaterar värdet så den inte är null
            //id = state.id + id,
            ,id = id

            )

        }

    }


    fun getUsername(username: String) {
        _uiState.update {
                state -> state.copy(
            username = username

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

    fun setBlog(titleValue: ArrayList<Blog>, blogpostValue: String, blogListValue: ArrayList<Blog>, ){

        _uiState.update {
                state -> state.copy(
            title = state.title + titleValue,
            blogpost = state.blogpost + blogpostValue,
            blogList = state.blogList?.plus(blogListValue)
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














