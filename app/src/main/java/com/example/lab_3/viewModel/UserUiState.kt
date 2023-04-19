package com.example.lab_3.viewModel

import com.example.lab_3.Blog

data class UserUiState (var id: String? = null,
                        var username: String? = null,
                        var password: String? = null,
                        var title: String? = null,
                        var blogpost: String? = null,
                        var blogList: List<Blog>? = emptyList()
                       ){



}