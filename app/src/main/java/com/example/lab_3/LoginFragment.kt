package com.example.lab_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.lab_3.databinding.FragmentLoginBinding
import com.example.lab_3.viewModel.UserViewModel
import com.google.firebase.database.*

class LoginFragment : Fragment(){
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DatabaseReference

  // val userViewModel by viewModels <UserViewModel>()
   private val viewModel: UserViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        //val viewModel: UserViewModel by activityViewModels()

        db = FirebaseDatabase
            .getInstance("https://lab-3-8ee48-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users") // är en rot som man pushar till "users", USER-TABLE, som @Entity i Room


        val etUsername = binding.etLoginUsername
        val etPassword = binding.etLoginUserPassword
        val btnLoginUser = binding.btnLoginUser
        var user: User
        val tvRegister = binding.tvRegister
        //var currentUser: User


        btnLoginUser.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {

                db.orderByChild("username")
                    .equalTo(username)
                    .addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(datasnapshot: DataSnapshot) {
                            if (datasnapshot.exists()){
                                for (userSnapshot in datasnapshot.children){
                                    val user = userSnapshot.getValue(User::class.java)
                                    if (user != null && user.username == username  && user.password == password){
                                        println("User match")
                                        Toast.makeText(context, "Match, Login Succeeded",
                                            Toast.LENGTH_SHORT).show()
                                        // calling getCurrentUser
                                        viewModel.setCurrentUser(user.username, title = "", blogpost = "",
                                           id = user.username, blogList = null)
                                        //val bundle = Bundle()
                                       // bundle.putString("username", username)
                                        Navigation.findNavController(view).navigate(
                                            R.id.action_loginFragment_to_userProfileFragment)
                                    }
                                    else{
                                        Toast.makeText(context, "Check username or password", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            }
                            else{
                                Toast.makeText(context, "No match, user not found, go to register", Toast.LENGTH_LONG).show()
                                println("No match")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                           println("Something went wrong , Error")
                        }

                    })

            }
            else{
                Toast.makeText(context, "Fill in all fields", Toast.LENGTH_SHORT).show()
            }
                // Check for user existence using the id and concatenated username and password


                //Toast.makeText(context,"user exist", Toast.LENGTH_LONG).show()


                /*
                                     if(user.username == username && user.password == password ) {
                                            println("User match, Login Succeeded")
                                            Toast.makeText(context,"Login Succeeded ", Toast.LENGTH_LONG).show()
                                            //viewModel.getUsername(username)
                                         viewModel.getCurrentUser(username,  "","","")

                                            Navigation.findNavController(view).navigate(
                                                R.id.action_loginFragment_to_userProfileFragment)

                                        } else {
                                            println("No match, try again")
                                            Toast.makeText(
                                                context, "No match! Try again",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }

                                      */


            }


            /*
                // Check username in database with addListenerForSingelValuEvent
                db.orderByChild("username").equalTo(username) // check all users with orderByChild
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

                                            viewModel.getUsername(username)
                                            println("Username: $user")

                                            Navigation.findNavController(view).navigate(
                                                R.id.action_loginFragment_to_userProfileFragment)

                                        }
                                        else{
                                            println("Password dose not match")
                                            Toast.makeText(context, "Password doesn't match",
                                                Toast.LENGTH_LONG).show()

                                        }
                                    }
                                }

                            }
                            else{
                                Toast.makeText(context, "User not found, please register", Toast.LENGTH_LONG).show()
                            }

                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(context,"Something went wrong $it", Toast.LENGTH_LONG).show()
                        }

                    })
                */
            tvRegister.setOnClickListener {
                Navigation.findNavController(view).navigate(
                    R.id.action_loginFragment_to_registerFragment
                )

            }


        return view
        }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    }

















