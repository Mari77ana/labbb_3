package com.example.lab_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lab_3.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var db : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = FirebaseDatabase
            .getInstance("https://lab-3-8ee48-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users") // är en rot som man pushar till "users", USER-TABLE

        val user = "-NRo-uC3avti7OSs0o8n"
        val userRef = FirebaseDatabase.getInstance().getReference("users/$user")

        //https://lab-3-8ee48-default-rtdb.europe-west1.firebasedatabase.app/

        val btnUserSubmit = binding.btnSubmitUser
        val tvUser = binding.tvUser
        val btnFetchUser = binding.btnFetchUser
        val etUsername = binding.etUsername
        val etUserPassword = binding.etUserPassword

        // DEFINE LISTENER,    skapar en egen listener, för att ändra på datan med onDataChange(),
        // som måste kopplas sedan med addvalueEventListener
        val userListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()
                tvUser.text = user.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Failed something went wrong ", Toast.LENGTH_LONG).show()

            }
        }

        // ON SUBMIT
        btnUserSubmit.setOnClickListener {

            val username = etUsername.text.toString()
            val userPassword = etUserPassword.text.toString()

            // TODO - CHECK IF EMPTY

            val newUser = User(username, userPassword, true)
            tvUser.text = username  //  tvUser.text = newUser.toString()


            // TODO - CHECK USERNAME: EXISTS?
            // Check if username exist - Fetch

            // TODO - PUSH TO DATABASE
            // setValue har 2 funktioner , pushar upp värdet, ändar/sätter nytt värdet
            db.push()
                .setValue(newUser)
                .addOnSuccessListener {

                Toast.makeText(this,"Succeeded!, Welcome $newUser", Toast.LENGTH_LONG).show()

            }.addOnFailureListener {
                Toast.makeText(this,"Failed something went wrong $it", Toast.LENGTH_LONG).show()
                // it står för exception
            }
            /*
            userRef.removeValue(
            ).addOnSuccessListener {  }

             */


        }

    }
}