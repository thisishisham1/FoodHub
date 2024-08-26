package iti.example.foodhub.presentation.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import iti.example.foodhub.R
import iti.example.foodhub.data.local.database.AppDatabase
import iti.example.foodhub.data.local.entity.User
import iti.example.foodhub.data.local.source.LocalDataSourceImpl
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.databinding.FragmentRegisterBinding
import iti.example.foodhub.sharedPref.SharedPrefHelper
import iti.example.foodhub.viewModel.authentication.AuthViewModel
import iti.example.foodhub.viewModel.authentication.AuthViewModelFactory

class RegisterFragment : Fragment() {

    private lateinit var roomRepository: RoomRepository
    private lateinit var sharedPrefHelper: SharedPrefHelper
    private var isPasswordVisible = false


    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(roomRepository, sharedPrefHelper)
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("RegisterFragment", "onCreateView: called")
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("RegisterFragment", "onCreate: called")
        super.onCreate(savedInstanceState)

        // Initialize SharedPrefHelper and RoomRepository here
        sharedPrefHelper = SharedPrefHelper(requireContext())
        roomRepository = RoomRepository(LocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).Dao()))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("RegisterFragment", "onViewCreated: called")

        // Handle sign-up button click
        binding.btnSignup.setOnClickListener {
            Log.d("RegisterFragment", "btnSignup: clicked")

            // Extract user input
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Proceed with registration logic
            viewModel.registerUser(
                User(
                    username = name,
                    email = email,
                    password = password
                ),
                onSuccess = {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                },
                onFailure = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )
        }
        // Handle login TextView click
        binding.tvlogin.setOnClickListener {
            Log.d("RegisterFragment", "tvLogin: clicked")
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        // Handle the eye icon click for password visibility toggle
        binding.eyeIconTextView.setOnClickListener {
            togglePasswordVisibility()
        }
    }


    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide the password
            binding.etPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.etPassword.setSelection(binding.etPassword.text.length) // Move the cursor to the end
            binding.eyeIconTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_red_eye_24, 0)
        } else {
            // Show the password
            binding.etPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.etPassword.setSelection(binding.etPassword.text.length) // Move the cursor to the end
            binding.eyeIconTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_red_eye_24, 0)
        }
        isPasswordVisible = !isPasswordVisible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("RegisterFragment", "onDestroyView: called")
        _binding = null
    }
}
