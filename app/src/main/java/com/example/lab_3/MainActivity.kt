package com.example.lab_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lab_3.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}



        // Exempel på radera user
        //val user = "-NRo-uC3avti7OSs0o8n"
       // val userRef = FirebaseDatabase.getInstance().getReference("users/$user")



        /*   Denna kod är överflödig , när vi har våra setOnClicks

        // DEFINE LISTENER,    skapar en egen listener, för att ändra på datan med onDataChange(),
        // som måste kopplas sedan med addvalueEventListener
        val userListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()
                tvUser.text = user.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"An error occurred $error", Toast.LENGTH_LONG).show()

            }
        }

         // Här kopplas den efter ändringen i onDataChange
        db.addValueEventListener(userListener)


         */

        /*
        TODO - QUERY INFO
        db.orderByChild("users")
        db.child("-NRyMab-r_Yyb8mZr0Iz")
         */

       /*
        // FETCH
       btnFetchUser.setOnClickListener {
           db.child("-NRyMab-r_Yyb8mZr0Iz")
               .get()
               .addOnSuccessListener {
                   val newUser = User(
                       it.child("username").value.toString(),
                       it.child("password").value.toString(),
                       it.child("isregister").value.toString().toBoolean()
                   )
                   // Kolla om namnet stämmer med användaren
                   // if(newUser.username == "Benny")
                   tvUser.text = newUser.username
                   //tvUser.text = it.value.toString() skriver ut hela info
               }

               .addOnFailureListener {
                   Toast.makeText(applicationContext,"An error occurred $it", Toast.LENGTH_LONG).show()
               }


       }

        */



            // TODO - CHECK IF EMPTY


           // tvUser.text = username  //  tvUser.text = newUser.toString()


            // TODO - CHECK USERNAME: EXISTS?
            // Check if username exist - Fetch



            // TODO - PUSH TO DATABASE
            // setValue har 2 funktioner , pushar upp värdet, ändar/sätter nytt värdet

            /*
            radera usern
            userRef.removeValue(
            ).addOnSuccessListener {  }

             */







