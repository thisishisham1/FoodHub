package iti.example.foodhub.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import iti.example.foodhub.R
import iti.example.foodhub.databinding.ActivityAuthBinding
import iti.example.foodhub.viewModel.authentication.AuthViewModel

class AuthActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Assuming you have set up your ViewModel with the repository
        viewModel.checkUserExists("user@example.com",
            onUserExists = {
                // Navigate to login page
            },
            onUserNotExists = {
                // Navigate to registration page
            }
        )
    }
}
