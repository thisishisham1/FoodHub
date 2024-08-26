package iti.example.foodhub.viewModel.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.presentation.auth.AuthActivity
import iti.example.foodhub.presentation.main.MainActivity
import iti.example.foodhub.presentation.splashView.SplashActivity
import iti.example.foodhub.sharedPref.SharedPrefHelper
import iti.example.foodhub.viewModel.authentication.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// SplashViewModel
class SplashViewModel(
    private val context: Context,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    init {
        Log.d("SplashViewModel", "init: ${sharedPrefHelper.getInt(AuthViewModel.KEY_USER_ID, -1)} ")
    }

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_USER_ID = "user_id"
    }

    fun navigateToAuth() {
        viewModelScope.launch {
            delay(3000)
            if (sharedPrefHelper.getBoolean(KEY_IS_LOGGED_IN, false)) {
                Log.d(
                    "SplashViewModel",
                    "navigateToAuth: ${sharedPrefHelper.getInt(KEY_USER_ID, -1)} "
                )
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
class SplashViewModelFactory(
    private val context: Context,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(context, sharedPrefHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}