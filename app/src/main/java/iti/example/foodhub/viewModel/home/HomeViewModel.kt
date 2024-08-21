package iti.example.foodhub.viewModel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.remote.responseModel.Meal
import iti.example.foodhub.data.repository.HomeRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "HomeViewModel"

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private var _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> = _meals

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getMealsByCategory(category: String) {
        Log.d(TAG, "getMealsByCategory: $category")
        viewModelScope.launch {
            _isLoading.value = true
            runBlocking {
                val response = homeRepository.getMealsByCategory(category)
                if (response.isSuccessful) {
                    _meals.value = response.body()?.meals.orEmpty()
                    Log.d(TAG, "getMealsByCategory: success")
                    Log.d(TAG, "getMealsByCategory: ${meals.value?.size}")
                } else {
                    Log.d(TAG, "getMealsByCategory: failed")
                }
            }
            _isLoading.value = false
        }
    }
}

class HomeViewModelFactory(private val homeRepository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository) as T
    }
}