package iti.example.foodhub.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import iti.example.foodhub.R
import iti.example.foodhub.data.local.database.AppDatabase
import iti.example.foodhub.data.local.source.LocalDataSourceImpl
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.presentation.main.MainActivity
import iti.example.foodhub.sharedPref.SharedPrefHelper
import iti.example.foodhub.viewModel.authentication.AuthViewModel
import iti.example.foodhub.viewModel.authentication.AuthViewModelFactory

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
    private lateinit var eyeIconTextView: TextView

    private var isPasswordVisible = false

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
        roomRepository =
            RoomRepository(LocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).Dao()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpNavigator = view.findViewById(R.id.tv_navigate_to_rigester)
        loginNavigator = view.findViewById(R.id.btn_login)
        emailInput = view.findViewById(R.id.et_email)
        passwordInput = view.findViewById(R.id.et_password)
        eyeIconTextView = view.findViewById(R.id.showPasswordIcon) // Your eye icon view

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
                    sharedPrefHelper.putString(AuthViewModel.KEY_USER_ID, email)
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                },
                onFailure = { errorMessage ->
                    // Show the error message as a toast
                    Toast.makeText(
                        requireContext(),
                        errorMessage ?: "Login failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        // Handle password visibility toggle
        eyeIconTextView.setOnClickListener {
            togglePasswordVisibility()
        }

        // Observe error messages from ViewModel
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide the password
            passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            eyeIconTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_red_eye_24, 0)
        } else {
            // Show the password
            passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            eyeIconTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_red_eye_24, 0)
        }
        passwordInput.setSelection(passwordInput.text.length) // Move the cursor to the end
        isPasswordVisible = !isPasswordVisible
    }
}
