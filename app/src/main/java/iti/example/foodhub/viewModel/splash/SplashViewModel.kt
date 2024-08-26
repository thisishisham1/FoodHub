package iti.example.foodhub.viewModel.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.presentation.auth.AuthActivity
import iti.example.foodhub.presentation.main.MainActivity
import iti.example.foodhub.presentation.splashView.SplashActivity
import iti.example.foodhub.sharedPref.SharedPrefHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val context: Context) : ViewModel() {

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"

    }

    private val sharedPreferences = SharedPrefHelper(context)
    fun navigateToAuth() {
        viewModelScope.launch {
            delay(3000)
            if (sharedPreferences.getBoolean(key = KEY_IS_LOGGED_IN)) {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as SplashActivity).finish()
            } else {
                val intent = Intent(context, AuthActivity::class.java)
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