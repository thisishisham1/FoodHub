package iti.example.foodhub.viewModel.Details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.remote.responseModel.Meals

import iti.example.foodhub.data.repository.HomeRepository
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _mealDetails = MutableLiveData<Meals>()//<DetailsUiModel>()
    val mealDetails: MutableLiveData<Meals> get() = _mealDetails


    fun getMealDetails(i: String) {
        viewModelScope.launch {

            _mealDetails.value = homeRepository.getMealsById(i)



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

