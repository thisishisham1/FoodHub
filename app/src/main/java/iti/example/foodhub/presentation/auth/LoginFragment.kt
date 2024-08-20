package iti.example.foodhub.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import iti.example.foodhub.R
import iti.example.foodhub.presentation.main.MainActivity


class LoginFragment : Fragment() {
    lateinit var signUpNavigator: TextView
    lateinit var loginNavigator: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        signUpNavigator = view.findViewById(R.id.tv_navigate_to_rigester)
        loginNavigator = view.findViewById(R.id.btn_login)
        signUpNavigator.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        loginNavigator.setOnClickListener {
            Intent(activity, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }


}