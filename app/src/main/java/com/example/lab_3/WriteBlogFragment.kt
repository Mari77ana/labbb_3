package com.example.lab_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.lab_3.databinding.FragmentWriteBlogBinding
import com.example.lab_3.viewModel.UserViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class WriteBlogFragment : Fragment() {
    private var _binding: FragmentWriteBlogBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: DatabaseReference
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWriteBlogBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        val view = binding.root


        db = FirebaseDatabase
            .getInstance("https://lab-3-8ee48-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users") // är en rot som man pushar till "users", USER-TABLE, som @Entity i Room


        val etWriteTitle = binding.etWriteTitle
        val etWriteBlogPost = binding.etWriteBlogPost
        val btnSubmitBlog = binding.btnSubmitBlog
        val btnRemoveTitle = binding.btnRemoveTitle
        val tvGoToProfile = binding.tvMyProfile
        var user: User
        var blog: Blog



        btnSubmitBlog.setOnClickListener {
            val title  = etWriteTitle.text.toString()
            val blogPost = etWriteBlogPost.text.toString()


            if (title.isNotEmpty() && blogPost.isNotEmpty()){
                val blogList = ArrayList<Blog>()
                blogList.add(Blog(title,blogPost))
               

                val userTitle = db.child("mariana")
                 userTitle.addListenerForSingleValueEvent(object : ValueEventListener{
                  override fun onDataChange(dataSnapshot: DataSnapshot) {
                        user = User()
                        val getUser = dataSnapshot.getValue<User>()
                      if (getUser != null){
                          user = User(getUser.username, getUser.password, blogList)
                      }
                        if (dataSnapshot.child("title").exists()) {
                            Toast.makeText(context, "Your title already exists", Toast.LENGTH_SHORT)
                                .show()

                        }
                        else{
                            db.push()
                            userTitle.setValue(user)
                                .addOnSuccessListener {
                                    println("Succeeded!")
                                }
                        }


                    }
                    override fun onCancelled(error: DatabaseError) {
                         Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show() 

                    }

                })

               }



                 /*
                val userTitle = db.child("mariana")
                println("Här är min userTitle$userTitle")
                userTitle.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        user = User()
                        val getUser = dataSnapshot.getValue<User>()
                        if (getUser != null){
                            user = User(getUser.username, getUser.password, blogList)
                        }
                        if (dataSnapshot.child("title").exists()) {
                            println(user.toString())
                            Toast.makeText(
                                context,
                                "Your title already exists. Try with another title",
                                Toast.LENGTH_LONG
                            ).show()
                        }


                        else{
                            db.push()
                          userTitle.setValue(user)
                                .addOnSuccessListener {
                                    println("Succeeded")
                                }
                        }


                        viewModel.getBlog(title,blogPost)

                        etWriteTitle.setText("")
                        etWriteBlogPost.setText("")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                    }

                })


            }
            else{
                Toast.makeText(context, "Please, fill in all fields", Toast.LENGTH_LONG).show()
            }

                  */
        }

        btnRemoveTitle.setOnClickListener {
            //userBlog = User(title = title, blogpost = blogPost)
           // val userTitle = db.child(etWriteTitle.text.toString())
            val userTitle = db.child("Blog")
            val titleRef = db.child(etWriteTitle.text.toString())
           titleRef.removeValue()
                .addOnSuccessListener {
                    println("Titeln har raderats från databasen")
                }
                .addOnFailureListener {
                    println("Ett fel uppstod när bloggen skulle raderas från databasen: ${it.message}")
                }

        }
        tvGoToProfile.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_writeBlogFragment_to_userProfileFragment)

        }


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}

