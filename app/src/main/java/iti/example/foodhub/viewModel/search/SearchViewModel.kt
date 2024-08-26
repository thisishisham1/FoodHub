package iti.example.foodhub.viewModel.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.repository.RemoteRepository
import iti.example.foodhub.presentation.mappper.toUiModel
import iti.example.foodhub.presentation.model.MealUiModel
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: RemoteRepository) : ViewModel() {
    private val _meals = MutableLiveData<List<MealUiModel>>()
    val meals: LiveData<List<MealUiModel>> = _meals

    fun getMealBySearch(query: String) {
        viewModelScope.launch {
            runCatching {
                val response = repository.getMealsBySearch(query)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val meals = responseBody.meals.map { it.toUiModel(false) }
                        _meals.value = meals ?: emptyList()
                        Log.d("SearchViewModel", "getMealBySearch: Success")
                        Log.d("SearchViewModel", "getMealBySearch: ${_meals.value}")
                    } else {
                        Log.e("SearchViewModel", "Response body is null")
                    }
                } else {
                    val errorMessage = "Error: ${response.code()} - ${response.message()}"
                    Log.e("SearchViewModel", errorMessage)
                }
            }.onFailure { exception ->
                val errorMessage = "Exception: ${exception.message}"
                Log.e("SearchViewModel", errorMessage, exception)
            }
        }
    }
}

class SearchViewModelFactory(private val repository: RemoteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}