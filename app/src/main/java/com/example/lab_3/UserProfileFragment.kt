package com.example.lab_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.lab_3.api.Quote
import com.example.lab_3.api.QuoteTextApi
import com.example.lab_3.databinding.FragmentUserProfileBinding
import com.example.lab_3.viewModel.UserUiState
import com.example.lab_3.viewModel.UserViewModel
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!


    private lateinit var db: DatabaseReference
    //private val userViewModel by viewModels <UserViewModel>()
    private val _uiState = MutableStateFlow(UserUiState())
    //  relations
    private val viewModel: UserViewModel by activityViewModels()

    // Bundle , sätt i en texView för att displaya username (den user som loggar in)
    // val username = argument?.getString("username")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        val tvQuote = binding.tvQuoteText
        val btnGoToBlog = binding.btnGoToBlog
        val tvLogout = binding.tvLogout
        val btnMyBlogs = binding.btnMyBlogs
        val tvBlogs = binding.tvBlogs
        val tvDisplayUsername = binding.tvDisplayUsername
        val user: User


        db = FirebaseDatabase
            .getInstance("https://lab-3-8ee48-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users") // är en rot som man pushar till "users", USER-TABLE, som @Entity i Room

        /*
        //val viewModel: UserViewModel by activityViewModels()
        //viewModel.userUiState.observe(viewLifecycleOwner){state -> tvDisplayUsername.text = state.username}
        //tvDisplayUsername.text = viewModel.uiState.value.username.toString()
/*
        userViewModel.uiState.collect(viewLifecycleOwner, Observer { state ->
            // Update UI with new username
           tvDisplayUsername.text = state.username
        })

 */
       */


        /*
        val tvDisplayTitle = binding.tvDisplayTitle
        val tvDisplayBlogpost = binding.tvDisplayBlogPost
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect(){
                    tvDisplayTitle.text = viewModel.uiState.value.title.toString() // Funkar
                    tvDisplayBlogpost.text = viewModel.uiState.value.blogList.toString() // Funkar
                }
            }
        }

         */


        // TODO Get quote API
        // (query - parameter)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.goprogram.ai")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val myQuote = retrofit.create<QuoteTextApi>().getQuoteText()

        myQuote.enqueue(object : Callback<Quote>{
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                if(response.isSuccessful){
                    val quote = response.body()
                    println("My quote: $quote")

                    if(quote != null){
                        tvQuote.text = quote.myProfileQuote
                    }
                }
                else{
                    println("ERROR")
                }
            }
            override fun onFailure(call: Call<Quote>, t: Throwable) {
                println(t.printStackTrace())
            }

        })

        // TODO  Get username
        if (viewModel.uiState.value.username != null ){
            db.child(viewModel.uiState.value.username.toString()).child("Blogs")
                .get()
                .addOnSuccessListener {
                    val user = User(
                        it.child("username").value.toString(),
                        )
                    tvDisplayUsername.text= viewModel.uiState.value.username.toString()
                }
        }

        // TODO Fetch blogs by button
        btnMyBlogs.setOnClickListener {
            if (viewModel.uiState.value.username != null) {
                db.child(viewModel.uiState.value.username.toString()).child("Blogs")
                    .get()
                    .addOnSuccessListener { dataSnapshot ->
                        if (dataSnapshot.exists()) {
                            val blogList = dataSnapshot.value as ArrayList<Blog>

                            tvBlogs.text= blogList.toString()
                        } else {
                            Toast.makeText(context, "No blogs found", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        /*
        btnMyBlogs.setOnClickListener {

            if (viewModel.uiState.value.username != null ){
                val userBlogRef = db.child(viewModel.uiState.value.username.toString()).child("Blogs")
                userBlogRef.child("Blogs")
                    .addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()){
                                val blogList = ArrayList<Blog>()
                                for (data in dataSnapshot.children) {
                                    val blog = data.getValue(Blog::class.java)
                                    if (blog != null) {
                                        val title = data.child("title").value.toString()
                                        val blogpost = data.child("blogpost").value.toString()
                                        blogList.add(Blog(title, blogpost))

                                    }
                                }
                                viewModel.uiState.value.blogList = blogList
                                tvBlogs.text = viewModel.uiState.value.blogList.toString()

                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })



            }
            else{
                Toast.makeText(context, "Sorry not found user or not found blogs", Toast.LENGTH_SHORT).show()
            }

        }

         */




        tvLogout.setOnClickListener {
           // viewModel.clearUsername()
            Navigation.findNavController(view).navigate(
                R.id.action_userProfileFragment_to_loginFragment)
        }

        btnGoToBlog.setOnClickListener {
            Navigation.findNavController(view).navigate(
                R.id.action_userProfileFragment_to_writeBlogFragment
            )
        }


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}