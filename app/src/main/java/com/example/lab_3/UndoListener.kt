package com.example.lab_3

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference


class UndoListener(var userRef: DatabaseReference, var regUsername: String): View.OnClickListener{

    override fun onClick(p0: View?) {
        userRef.removeValue()
       println("User deleted")
        //Toast.makeText(this,"User deleted", Toast.LENGTH_LONG)

     }

}