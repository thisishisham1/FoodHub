package iti.example.foodhub.viewModel.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.database.User
import iti.example.foodhub.data.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    fun registerUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val existingUser = repository.checkUser(user.email)
            if (existingUser == null) {
                repository.registerUser(user)
                onSuccess()
            } else {
                onError("User already exists!")
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: (User) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val user = repository.loginUser(email, password)
            if (user != null) {
                onSuccess(user)
            } else {
                onError("Invalid credentials!")
            }
        }
    }

    fun checkUserExists(email: String, onUserExists: () -> Unit, onUserNotExists: () -> Unit) {
        viewModelScope.launch {
            val user = repository.checkUser(email)
            if (user != null) {
                onUserExists()
            } else {
                onUserNotExists()
            }
        }
    }
}
