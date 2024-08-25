package iti.example.foodhub.viewModel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.local.entity.Favorite
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.remote.responseModel.Meal
import iti.example.foodhub.data.repository.HomeRepository
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.presentation.mappper.toUiModel
import iti.example.foodhub.presentation.model.MealUiModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "HomeViewModel"

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {
    private var _meals = MutableLiveData<List<MealUiModel>>()
    val meals: LiveData<List<MealUiModel>> = _meals


    fun getMealsByCategory(category: String) {
        Log.d(TAG, "getMealsByCategory: $category")
        viewModelScope.launch {
            runBlocking {
                val response = homeRepository.getMealsByCategory(category)
                if (response.isSuccessful) {
                    _meals.value = response.body()?.meals?.map {
                        it.toUiModel(false)
                    }
                } else {
                    Log.d(TAG, "getMealsByCategory: failed")
                }
            }
        }
    }

    fun favoriteClickedHandle(meal: MealUiModel, userId: Int) {
        viewModelScope.launch {
            Log.d(TAG, "favoriteClickedHandle: Toggling favorite for meal: ${meal.idMeal}")
            runCatching {
                toggleFavorite(meal = meal, userId)
            }.onFailure { exception ->
                Log.e(TAG, "favoriteClickedHandle: Error toggling favorite", exception)
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
            Log.e(TAG, "toggleFavorite: Error updating favorite", exception)
        }
    }
}

class HomeViewModelFactory(
    private val homeRepository: HomeRepository,
    private val roomRepository: RoomRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository, roomRepository) as T
    }
}