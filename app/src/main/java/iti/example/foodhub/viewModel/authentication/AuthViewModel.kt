package iti.example.foodhub.viewModel.authentication

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.local.entity.User
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.presentation.auth.AuthActivity
import iti.example.foodhub.presentation.main.MainActivity
import kotlinx.coroutines.launch

const val TAG = "AuthViewModel"

class AuthViewModel(private val roomRepository: RoomRepository) : ViewModel() {

    // Function to validate sign-up input
    fun validateSignUp(name: String, email: String, password: String): String? {
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

    // Register user with Room database
    fun registerUser(user: User) {
        viewModelScope.launch {
            runCatching {
                roomRepository.registerUser(user)
            }.onSuccess {
                Log.d(TAG, "registerUser: user registered successfully")
            }.onFailure {
                Log.e(TAG, "registerUser: registration failed", it)
            }
        }
    }

    fun loginUser(
        email: String,
        password: String,
        context: Context,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                roomRepository.loginUser(email, password)
            }.onSuccess { user ->
                if (user != null) {
                    Log.d(TAG, "loginUser: user logged in successfully")
                    onSuccess(user)
                    navigateToMain(context = context)
                } else {
                    Log.d(TAG, "loginUser: user not found or incorrect password")
                    onError("User not found or incorrect password")
                }
            }.onFailure {
                onError("Login failed: ${it.message}")
                Log.e(TAG, "loginUser: login failed", it)
            }
        }
    }

    private fun navigateToMain(context: Context) {
        Intent(context, MainActivity::class.java).also { intent ->
            context.startActivity(intent)
            (context as AuthActivity).finish()
        }
    }

    fun checkUserExists(email: String, onUserExists: () -> Unit, onUserNotExists: () -> Unit) {
        viewModelScope.launch {
            // TODO: Implement check user exists functionality
        }
    }
}

class AuthViewModelFactory(private val roomRepository: RoomRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(roomRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
