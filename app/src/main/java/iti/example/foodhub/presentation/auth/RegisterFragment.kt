package iti.example.foodhub.presentation.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        roomRepository =
            RoomRepository(LocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).Dao()))
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("RegisterFragment", "onViewCreated: called")
        viewModel.registerUser(
            User(
                username = "hisham mohamed",
                email = "h19@gmail.com",
                password = "123456"
            )
        )
        // Handle sign-up button click
        binding.btnSignup.setOnClickListener {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("RegisterFragment", "onDestroyView: called")
        _binding = null
    }
}