package iti.example.foodhub.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import iti.example.foodhub.R
import iti.example.foodhub.viewModel.authentication.AuthViewModel
import iti.example.foodhub.data.local.database.AppDatabase
import iti.example.foodhub.data.repository.UserRepository
import iti.example.foodhub.viewModel.authentication.AuthViewModelFactory

class AuthActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Initialize the database and repository
        val database = AppDatabase.getDatabase(this)
        val userRepository = UserRepository(database.userDao())

        // Initialize the ViewModel
        val viewModelFactory = AuthViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        // Find the NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Check if the user exists
//        viewModel.checkUserExists("user@example.com",
//            onUserExists = {
//                // Navigate to login fragment
//                navController.navigate(R.id.loginFragment)
//            },
//            onUserNotExists = {
//                // Navigate to registration fragment
//                navController.navigate(R.id.registerFragment)
//            }
//        )
    }
}
