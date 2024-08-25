package iti.example.foodhub.presentation.auth

import android.content.Intent
import android.os.Bundle
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


class LoginFragment : Fragment() {
    private lateinit var roomRepository: RoomRepository

    private val viewModel: AuthViewModel by viewModels(
        factoryProducer = { AuthViewModelFactory(roomRepository) }
    )
    lateinit var signUpNavigator: TextView
    lateinit var loginNavigator: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomRepository =
            RoomRepository(LocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).Dao()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loginUser(
            "h9@gmail.com",
            "123456",
            onSuccess = {},
            onError = {},
            context = requireContext()
        )
        signUpNavigator = view.findViewById(R.id.tv_navigate_to_rigester)
        loginNavigator = view.findViewById(R.id.btn_login)
        signUpNavigator.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        loginNavigator.setOnClickListener {
        }
    }


}