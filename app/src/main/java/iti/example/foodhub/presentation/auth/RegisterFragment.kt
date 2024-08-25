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
import iti.example.foodhub.data.local.database.AppDatabase
import iti.example.foodhub.data.local.entity.User
import iti.example.foodhub.data.local.source.LocalDataSourceImpl
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.databinding.FragmentRegisterBinding
import iti.example.foodhub.viewModel.authentication.AuthViewModel
import iti.example.foodhub.viewModel.authentication.AuthViewModelFactory

class RegisterFragment : Fragment() {

    private lateinit var roomRepository: RoomRepository

    private val viewModel: AuthViewModel by viewModels(
        factoryProducer = { AuthViewModelFactory(roomRepository) }
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
        roomRepository = RoomRepository(LocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).Dao()))
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

            // Validate input using ViewModel
            val errorMessage = viewModel.validateSignUp(name, email, password)
            if (errorMessage == null) {
                // Proceed with registration logic
                viewModel.registerUser(
                    User(
                        username = name,
                        email = email,
                        password = password
                    )
                )

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
}
