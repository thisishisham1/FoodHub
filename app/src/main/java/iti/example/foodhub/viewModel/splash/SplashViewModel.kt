package iti.example.foodhub.viewModel.splash

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.presentation.auth.AuthActivity
import iti.example.foodhub.presentation.main.MainActivity
import iti.example.foodhub.presentation.splashView.SplashActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val context: Context) : ViewModel() {

    fun navigateToAuth() {
        viewModelScope.launch {
            delay(3000)
            Intent(context, AuthActivity::class.java).also { intent ->
                context.startActivity(intent)
                (context as SplashActivity).finish()
            }
        }
    }
}

class SplashViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}