package iti.example.foodhub.viewModel.Details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.repository.DetailsRepositry
import kotlinx.coroutines.launch

class DetailsViewModel (private val productRepository:DetailsRepositry) : ViewModel() {
    private val _mealList = MutableLiveData<List<Meals>>()
    val mealList: LiveData<List<Meals>> get() = _mealList

    private val _detailsList = MutableLiveData<List<String>>()
    val detailsList: LiveData<List<String>> get() = _detailsList

    init {
        getMeals()
    }

    private fun getMeals() {
        viewModelScope.launch {
            _mealList.value = productRepository.getProducts()
        }
    }

    fun getDetails(id: Int) {
        viewModelScope.launch {
            val meal = productRepository.getProductById(id)
            _detailsList.value = when (meal.strImageSource) {
                is List<*> -> meal.strImageSource as List<String>
                is String -> listOf(meal.strImageSource)
                else -> emptyList()
            }
        }
    }

}
class AllMealsViewModelFactory(private val productRepository: DetailsRepositry) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return AllMealsViewModelFactory(productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
/*
    fun getDetails(id: Int) {
        viewModelScope.launch {
            val meal = productRepository.getProductById(id)
            _detailsList.value = meal.strImageSource?: emptyList()
        }
    }
    */
