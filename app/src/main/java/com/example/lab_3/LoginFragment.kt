package com.example.lab_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lab_3.databinding.FragmentLoginBinding
import com.example.lab_3.databinding.FragmentRegisterBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        val view = binding.root


        db = FirebaseDatabase
            .getInstance("https://lab-3-8ee48-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users") // är en rot som man pushar till "users", USER-TABLE, som @Entity i Room


        val etUsername = binding.etLoginUsername
        val etPassword = binding.etLoginUserPassword
        val btnLoginUser = binding.btnLoginUser
        var user : User?

      btnLoginUser.setOnClickListener {

            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()){
                // Check username in database with addListenerForSingelValuEvent
                db.orderByChild("username").equalTo(username)
                    .addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (userSnapshot in dataSnapshot.children){
                                    val user = userSnapshot.getValue(User::class.java)
                                    if (user != null) {
                                        if (user.password == password){
                                            println("password  match")
                                            Toast.makeText(context, "WELCOME",
                                                Toast.LENGTH_SHORT).show()
                                        }
                                        else{
                                            println("Password dose not match")
                                            Toast.makeText(context, "Password doesn't match",
                                                Toast.LENGTH_LONG).show()

                                        }
                                    }
                                    /*
                                    else{
                                        Toast.makeText(context, "User dose not exist. " +
                                                "You have to be registered",
                                        Toast.LENGTH_LONG).show()
                                    }

                                     */
                                }

                            }
                            else{
                                Toast.makeText(context, "No user in database", Toast.LENGTH_LONG).show()
                            }



                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(context,"Something went wrong $it", Toast.LENGTH_LONG).show()
                        }

                    })


            }
            else{
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
            }

        }


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}