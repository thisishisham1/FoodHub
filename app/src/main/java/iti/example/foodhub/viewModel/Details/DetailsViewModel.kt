package iti.example.foodhub.viewModel.Details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.remote.responseModel.ResponseDetailsModel
import iti.example.foodhub.data.remote.responseModel.ResponseMealsModel

import iti.example.foodhub.data.repository.HomeRepository
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _mealDetails = MutableLiveData<ResponseDetailsModel>()
    val mealDetails: LiveData<ResponseDetailsModel> get() = _mealDetails


    fun getMealDetails(i: String) {
        Log.d("MealDetailsViewModel", "getMealDetails() called with ID: $i")
        viewModelScope.launch {
            try {
                val list = homeRepository.getMealsById(i)
                _mealDetails.value = list
            } catch (e: Exception) {
                Log.e("MealDetailsViewModel", "Error fetching meal details", e)
            }

        }
    }
}

class MealDetailsViewModelFactory(
    private val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealDetailsViewModel(homeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

