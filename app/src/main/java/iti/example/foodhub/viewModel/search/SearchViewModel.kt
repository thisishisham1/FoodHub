package iti.example.foodhub.viewModel.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.local.entity.Favorite
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.repository.RemoteRepository
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.presentation.mappper.toUiModel
import iti.example.foodhub.presentation.model.MealUiModel
import iti.example.foodhub.sharedPref.SharedPrefHelper
import iti.example.foodhub.viewModel.home.HomeViewModel.Companion.KEY_USER_ID
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

const val TAG = "SearchViewModel"

class SearchViewModel(
    private val repository: RemoteRepository,
    private val roomRepository: RoomRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {
    private val _meals = MutableLiveData<List<MealUiModel>>()
    val meals: LiveData<List<MealUiModel>> = _meals

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

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

    private fun getLoggedInUserId(): Int {
        return sharedPrefHelper.getInt(KEY_USER_ID, -1) // Return user ID or -1 if not found
    }

    fun favoriteClickedHandle(meal: MealUiModel) {
        val userId = getLoggedInUserId()
        viewModelScope.launch {
            runCatching {
                toggleFavorite(meal = meal, userId)
            }.onFailure { exception ->
                if (exception is SocketTimeoutException) {
                    handleSocketTimeoutException(exception)
                } else {
                    Log.e(TAG, "favoriteClickedHandle: Error toggling favorite", exception)
                }
            }
        }
    }

    private suspend fun toggleFavorite(meal: MealUiModel, userId: Int) {
        runCatching {
            roomRepository.insertItem(Item(itemId = meal.idMeal.toInt(), itemName = meal.strMeal))
            val updatedMeals = _meals.value!!.map {
                if (it.idMeal == meal.idMeal) {
                    val updateMeal = it.copy(isFavorite = !it.isFavorite)
                    if (updateMeal.isFavorite) {
                        Log.d(TAG, "toggleFavorite: Adding favorite for meal: ${meal.idMeal}")
                        roomRepository.insertFavorite(Favorite(userId, meal.idMeal.toInt()))
                    } else {
                        Log.d(TAG, "toggleFavorite: Removing favorite for meal: ${meal.idMeal}")
                        roomRepository.deleteFavorite(Favorite(userId, meal.idMeal.toInt()))
                        roomRepository.deleteItemById(meal.idMeal.toInt())
                    }
                    updateMeal
                } else {
                    it
                }
            }
            _meals.value = updatedMeals
            Log.d(TAG, "toggleFavorite: Updated meals list")
        }.onFailure { exception ->
            if (exception is SocketTimeoutException) {
                handleSocketTimeoutException(exception)
            } else {
                Log.e(TAG, "toggleFavorite: Error updating favorite", exception)
            }
        }
    }

    private fun handleSocketTimeoutException(exception: SocketTimeoutException) {
        _error.value = "Network timeout. Please try again later."
    }

}

class SearchViewModelFactory(
    private val repository: RemoteRepository,
    private val roomRepository: RoomRepository,
    private val sharedPrefHelper: SharedPrefHelper
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repository, roomRepository, sharedPrefHelper) as T
    }
}