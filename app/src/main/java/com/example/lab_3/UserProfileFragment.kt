package com.example.lab_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!


    //private val userViewModel by viewModels <UserViewModel>()
    private val _uiState = MutableStateFlow(UserUiState())
    private val viewModel: UserViewModel by activityViewModels()

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

        val tvDisplayUsername = binding.tvDisplayUsername

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect(){
                    tvDisplayUsername.text = viewModel.uiState.value.username.toString()

                }
            }
        }

        val tvDisplayTitle = binding.tvDisplayTitle
        val tvDisplayBlogpost = binding.tvDisplayBlogPost
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect(){
                    tvDisplayTitle.text = viewModel.uiState.value.title.toString()
                    tvDisplayBlogpost.text = viewModel.uiState.value.blogpost.toString()
                }
            }
        }



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

        tvLogout.setOnClickListener {
            viewModel.clearUsername()
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