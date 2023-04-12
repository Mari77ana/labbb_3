package com.example.lab_3

import androidx.lifecycle.ViewModel

 class User (
    //Best Practise, gör om till optional ?, ifall värdet vi får ut fr databasen är null,
    // programmet hanterar det, men ibland måste man kolla att värdet inte ska vara null, kallas casting
    // viktigt, så länge man kollar att värdet kommer in
    val username: String? = null,
    val password: String? = null,
    val blogList: List<Blog>? = null

    ){




}

/*
 val blogList = listOf(){
         Blog(title = null, blogpost = null)
     }
 */