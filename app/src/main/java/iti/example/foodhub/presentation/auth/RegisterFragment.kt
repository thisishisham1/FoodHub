package iti.example.foodhub.presentation.auth

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
import iti.example.foodhub.data.local.dao.UserDao
import iti.example.foodhub.data.local.database.AppDatabase
import iti.example.foodhub.databinding.FragmentRegisterBinding
import iti.example.foodhub.viewModel.authentication.AuthViewModel
import iti.example.foodhub.viewModel.authentication.AuthViewModelFactory

class RegisterFragment : Fragment() {
    private lateinit var dao: UserDao

    private val viewModel: AuthViewModel by viewModels(
        factoryProducer = { AuthViewModelFactory(dao) }
    )

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
        dao = AppDatabase.getDatabase(requireContext()).userDao()
        super.onCreate(savedInstanceState)
    }

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

            // Validate input
            val errorMessage = validateSignUp(name, email, password)
            if (errorMessage == null) {
                // Proceed with registration logic, e.g., saving data to the database
                registerUser(name, email, password)

                // Navigate to LoginFragment after successful registration
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                // Show error message to the user
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("RegisterFragment", "onDestroyView: called")
        _binding = null
    }

    // Function to validate sign-up input
    private fun validateSignUp(name: String, email: String, password: String): String? {
        // Check if name is empty
        if (name.isBlank()) {
            return "Name cannot be empty"
        }

        // Check if email is valid using regular expression
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        if (!email.matches(emailPattern)) {
            return "Please enter a valid email address"
        }

        // Check if password meets the criteria
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$")
        if (!password.matches(passwordPattern)) {
            return """
                Password must be at least 8 characters,
                contain both uppercase and lowercase letters,
                and include at least one special character.
            """.trimIndent()
        }

        // If all checks pass, return null (no error)
        return null
    }

    // Dummy function to represent user registration logic
    private fun registerUser(name: String, email: String, password: String) {
        // Registration logic, such as saving data to Room or making an API call
        Log.d("RegisterFragment", "User registered: Name=$name, Email=$email")
    }
}
