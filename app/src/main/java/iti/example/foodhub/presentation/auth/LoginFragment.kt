package iti.example.foodhub.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import iti.example.foodhub.R
import iti.example.foodhub.data.local.database.AppDatabase
import iti.example.foodhub.data.local.source.LocalDataSourceImpl
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.presentation.main.MainActivity
import iti.example.foodhub.viewModel.authentication.AuthViewModel
import iti.example.foodhub.viewModel.authentication.AuthViewModelFactory


import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import iti.example.foodhub.sharedPref.SharedPrefHelper

class LoginFragment : Fragment() {
    private lateinit var roomRepository: RoomRepository
    private lateinit var sharedPrefHelper: SharedPrefHelper

    private val viewModel: AuthViewModel by viewModels(
        factoryProducer = { AuthViewModelFactory(roomRepository, sharedPrefHelper) }
    )
    private lateinit var signUpNavigator: TextView
    private lateinit var loginNavigator: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("LoginFragment", "onCreate: called")
        super.onCreate(savedInstanceState)

        sharedPrefHelper = SharedPrefHelper(requireContext())
        roomRepository = RoomRepository(LocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).Dao()))

        if (viewModel.checkLoginStatus()) {
            // User is already logged in; navigate to the main activity
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpNavigator = view.findViewById(R.id.tv_navigate_to_rigester)
        loginNavigator = view.findViewById(R.id.btn_login)
        emailInput = view.findViewById(R.id.et_email)
        passwordInput = view.findViewById(R.id.et_password)

        // Navigate to the registration fragment
        signUpNavigator.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Handle login button click

        loginNavigator.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            viewModel.loginUser(
                email = email,
                password = password,
                onSuccess = {
                    // Save login status and navigate to the main activity on successful login
                    sharedPrefHelper.putBoolean(AuthViewModel.KEY_IS_LOGGED_IN, true)
                    sharedPrefHelper.putString(AuthViewModel.KEY_USER_EMAIL, email)
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                },
                onFailure = { errorMessage ->
                    // Show the error message as a toast
                    Toast.makeText(requireContext(), errorMessage ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            )
        }



        // Observe error messages from ViewModel
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
