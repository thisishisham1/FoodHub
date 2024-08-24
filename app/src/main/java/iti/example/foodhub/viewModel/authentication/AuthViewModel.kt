package iti.example.foodhub.viewModel.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.local.dao.UserDao
import iti.example.foodhub.data.local.entity.User
import iti.example.foodhub.data.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val dao: UserDao) : ViewModel() {

    init {
        Log.d("AuthViewModel", "init: called")
        registerUser(
            User(
                username = "hisham mohameed",
                email = "h19@gmail.com",
                password = "123456"
            )
        )
    }

    private fun registerUser(user: User) {
        viewModelScope.launch {
            Log.d("AuthViewModel", "registerUser: entered registerUser")
            dao.registerUser(user)
            Log.d("AuthViewModel", "registerUser:  successfully registered")
        }
    }

    fun loginUser(
        email: String,
        password: String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val user = dao.loginUser(email, password)
            if (user != null) {
                onSuccess(user)
            } else {
                onError("Invalid credentials!")
            }
        }
    }

    fun checkUserExists(email: String, onUserExists: () -> Unit, onUserNotExists: () -> Unit) {
        viewModelScope.launch {
            onUserExists()
            onUserNotExists()

        }
    }
}
