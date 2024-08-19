package iti.example.foodhub.presentation.main.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import iti.example.foodhub.R


class WelcomeFragment : Fragment() {


    lateinit var getStartedButton:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getStartedButton=view.findViewById(R.id.btn_get_started)
        getStartedButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_welcomeFragment2_to_loginFragment)

        }
    }



}