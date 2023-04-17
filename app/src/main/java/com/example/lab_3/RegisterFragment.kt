package com.example.lab_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.lab_3.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        val view = binding.root


        db = FirebaseDatabase
            .getInstance("https://lab-3-8ee48-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users") // är en rot som man pushar till "users", USER-TABLE, som @Entity i Room

        val btnRegisterUser = binding.btnRegisterUser
        val etRegisterUsername = binding.etRegisterUsername
        val etRegisterUserPassword = binding.etRegisterUserPassword
        val btnToLoginPage = binding.btnLogPage
        var newUser: User


        btnRegisterUser.setOnClickListener {
            val regUsername = etRegisterUsername.text.toString()
            val regUserPassword = etRegisterUserPassword.text.toString()
            newUser = User(regUsername, regUserPassword)


            if (regUsername.isNotEmpty() && regUserPassword.isNotEmpty()) {
                val userRef = db.child(regUsername) // referensen
                userRef.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(dataSnapshot.exists()) {
                            println("User exists")
                            Toast.makeText(context, "User already exists. Try with another username", Toast.LENGTH_LONG).show()
                        }
                        else{
                            //push newUser
                            db.push()   // kanske onödig
                            userRef.setValue(newUser)
                                .addOnSuccessListener {

                                   Snackbar.make(view, "Succeeded! Now you can login", Snackbar.LENGTH_LONG).setAction("UNDO",
                                  // UndoListener(newUser = userRef)).show()
                                      // UndoListener(userRef.child(regUsername))).show()
                                       UndoListener(userRef, regUsername)).show()
                                }
                        }

                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context,"Something went wrong $it", Toast.LENGTH_LONG).show()
                    }

                })
                println(newUser.toString())
            }
            else {
                Toast.makeText(context, "Fill in all fields please!", Toast.LENGTH_LONG).show()
            }
                 /*
                db.push()
                    .setValue(newUser)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Succeeded", Toast.LENGTH_SHORT).show()
                    }

                 */
                   /*
                db.push()
                    .setValue(newUser)
                    .addOnSuccessListener {
                        Toast.makeText(context,"Succeeded", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context,"Something went wrong $it", Toast.LENGTH_LONG).show()

                    }

                 */
            /*
            db.child("users").equalTo(regUsername).addListenerForSingleValueEvent(
                object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            Toast.makeText(context,"Username already exist!", Toast.LENGTH_LONG).show()
                        }
                        else{
                            db.push()
                                .setValue(newUser)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Succeeded", Toast.LENGTH_SHORT).show()
                                }


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context,"Something went wrong $it", Toast.LENGTH_LONG).show()
                        // it står för exception
                    }

                })

             */
           /*
              db.orderByChild("regUsername").equalTo(regUsername)
                  .addListenerForSingleValueEvent(object : ValueEventListener{
                  override fun onDataChange(snapshot: DataSnapshot) {
                     if(snapshot.exists()){
                         Toast.makeText(context,"Username already exist!", Toast.LENGTH_LONG).show()
                     }
                      else{
                          db.push()
                              .setValue(newUser)
                              .addOnSuccessListener {
                                  Toast.makeText(context, "Succeeded", Toast.LENGTH_SHORT).show()
                              }
                      }
                  }
                  override fun onCancelled(error: DatabaseError) {
                      Toast.makeText(context,"Something went wrong $it", Toast.LENGTH_LONG).show()
                      // it står för exception
                  }

              })

             */

        }
        btnToLoginPage.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)

        }

        return view

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
