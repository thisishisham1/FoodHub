package iti.example.foodhub.viewModel.authentication

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.local.entity.User
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.sharedPref.SharedPrefHelper
import kotlinx.coroutines.launch

const val TAG = "AuthViewModel"

class AuthViewModel(
    private val roomRepository: RoomRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_USER_EMAIL = "user_email"
    }

    private fun saveLoginStatus(email: String) {
        sharedPrefHelper.putBoolean(KEY_IS_LOGGED_IN, true)
        sharedPrefHelper.putString(KEY_USER_EMAIL, email)
    }


    fun signOut() {
        sharedPrefHelper.clear()  // Clear all shared preferences
    }


    fun isUserLoggedIn(): Boolean {
        return sharedPrefHelper.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    private fun validateSignUp(name: String, email: String, password: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult.Error("Name cannot be empty")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult.Error("Please enter a valid email address")
        }

        if (password.length < 8 || !password.any { it.isLowerCase() } ||
            !password.any { it.isUpperCase() } || !password.any { !it.isLetterOrDigit() }
        ) {
            return ValidationResult.Error(
                "Password must be at least 8 characters, contain both uppercase and lowercase letters, and include at least one special character."
            )
        }

        return ValidationResult.Success
    }

    fun registerUser(user: User, onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            when (val result = validateSignUp(user.username, user.email, user.password)) {
                is ValidationResult.Success -> {
                    runCatching {
                        roomRepository.registerUser(user)
                    }.onSuccess {
                        onSuccess()
                        Log.d(TAG, "registerUser: user registered successfully")
                    }.onFailure {
                        _errorMessage.value = "Registration failed: ${it.message}"
                        Log.e(TAG, "registerUser: registration failed", it)
                    }
                }
                is ValidationResult.Error -> {
                    _errorMessage.value = result.message
                    onFailure(result.message)
                }
            }
        }
    }
    fun checkLoginStatus(): Boolean {
        return sharedPrefHelper.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            if (email.isBlank() || password.isBlank()) {
                _errorMessage.value = "Email and password cannot be empty"
                onFailure(_errorMessage.value)
                return@launch
            }

            runCatching {
                roomRepository.loginUser(email, password)
            }.onSuccess { user ->
                if (user != null) {
                    saveLoginStatus(email)
                    Log.d(TAG, "loginUser: user logged in successfully")
                    onSuccess()
                } else {
                    _errorMessage.value = "User not found or incorrect password"
                    onFailure(_errorMessage.value)
                }
            }.onFailure { throwable ->
                _errorMessage.value = "Login failed: ${throwable.message}"
                Log.e(TAG, "loginUser: login failed", throwable)
                onFailure(_errorMessage.value)
            }
        }
    }
}

class AuthViewModelFactory(
    private val roomRepository: RoomRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(roomRepository, sharedPrefHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}
