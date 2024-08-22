package iti.example.foodhub.viewModel.Details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.repository.DetailsRepositry
import iti.example.foodhub.presentation.mappper.DetailsUiModelMapper
import iti.example.foodhub.presentation.model.DetailsUiModel
import kotlinx.coroutines.launch

class MealDetailsViewModel(private val detailsRepository: DetailsRepositry,
    private val mapper : DetailsUiModelMapper
) : ViewModel() {

    private val _mealDetails = MutableLiveData<DetailsUiModel>()
    val mealDetails: MutableLiveData<DetailsUiModel> get() = _mealDetails

    fun getMealDetails(id: String) {
        viewModelScope.launch {
            val meal = detailsRepository.getProductById(id)

            _mealDetails.value=mapper.mapToUiModel(meal)
        }
    }
}

class MealDetailsViewModelFactory(private val detailsRepository: DetailsRepositry,
    private val mapper : DetailsUiModelMapper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealDetailsViewModel(detailsRepository,mapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

