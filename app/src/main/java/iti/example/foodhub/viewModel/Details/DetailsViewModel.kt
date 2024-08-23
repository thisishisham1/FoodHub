package iti.example.foodhub.viewModel.Details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.repository.DetailsRepository
import iti.example.foodhub.data.repository.HomeRepository
import iti.example.foodhub.presentation.mappper.toDetailsUi
import iti.example.foodhub.presentation.model.DetailsUiModel
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _mealDetails = MutableLiveData<Meals>()//<DetailsUiModel>()
    val mealDetails: MutableLiveData<Meals> get() = _mealDetails

    init {
        getMealDetails("52772")
    }

    fun getMealDetails(id: String) {
        viewModelScope.launch {
            Log.d("DetailsViewModel", "getMealDetails: entered")
            _mealDetails.value = homeRepository.getProductById(id)
            Log.d("DetailsViewModel", "getMealDetails: closed")
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

