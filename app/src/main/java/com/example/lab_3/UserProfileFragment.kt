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
import com.example.lab_3.databinding.FragmentUserProfileBinding
import com.example.lab_3.viewModel.UserUiState
import com.example.lab_3.viewModel.UserViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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
        /*
        // Observera förändringar på UIState
        viewModel.uiState.collect() { uiState ->
            // Uppdatera användargränssnittet med det nya användarnamnet
            val username = uiState.username
            tvDisplayUsername.text = username
        }

         */

       */
      /*
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect() {

                   tvDisplayUsername.text= viewModel.uiState.value.username.toString()
                }

            }

        }

 */
      /*
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    tvDisplayUsername.text = uiState.username.toString()
                }
            }
        }

 */
        /*
        // observera username variabeln i view modellen och uppdatera vyn när den ändras. Den funkar
        lifecycleScope.launch {
            viewModel.username.collect {
                tvDisplayUsername.text = it
            }
        }

         */
        /*
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.username.collect {
                    tvDisplayUsername.text = viewModel.username.value.toString()
                }

            }
        }

         */
        val tvDisplayUsername = binding.tvDisplayUsername

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect(){
                    tvDisplayUsername.text = viewModel.uiState.value.username.toString()
                }
            }
        }

        return view


        /*
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                val username = uiState.username
                view.tv= username
            }
        }

         */
    /*
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.uiState.collect() {
                   // userViewModel.getUsername(username = userViewModel.toString())
                   // userViewModel.getUsername(username = _uiState.value.username)
                    //userViewModel.uiState.value.username
                    //userViewModel.getUsername(username = _uiState.value.username)
                    tvDisplayUsername.text = userViewModel.uiState.value.username


                }

            }
        }

         */

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}