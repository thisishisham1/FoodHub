package iti.example.foodhub.viewModel.fav

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
import iti.example.foodhub.presentation.model.MealUiModel
import iti.example.foodhub.sharedPref.SharedPrefHelper
import iti.example.foodhub.viewModel.authentication.TAG
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

private const val TAG = "FavouriteViewModel"

class FavouriteViewModel(
    private val remoteRepository: RemoteRepository,
    private val roomRepository: RoomRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    private val _favoriteMeals = MutableLiveData<List<MealUiModel>>()
    val favoriteMeals: LiveData<List<MealUiModel>> = _favoriteMeals

    init {
        getFavoriteMeals()
    }

    private fun getFavoriteMeals() {
        val userId = sharedPrefHelper.getInt("user_id", -1)
        if (userId == -1) {
            _favoriteMeals.value = emptyList()
            return
        }
        viewModelScope.launch {
            runCatching {
                val list = roomRepository.getUserFavorites(userId)
                _favoriteMeals.value = list.map { it.toUiModel(true) }
                Log.d("FavouriteViewModel", "getFavoriteMeals: $list")
            }.onSuccess {
                Log.d("FavouriteViewModel", "Is success ")
            }.onFailure {
                _favoriteMeals.value = emptyList()
                Log.d("FavouriteViewModel", "failure: ")
            }
        }
    }

    fun favoriteClickedHandle(meal: MealUiModel) {
        val userId = getLoggedInUserId()
        viewModelScope.launch {
            Log.d(TAG, "favoriteClickedHandle: Toggling favorite for meal: ${meal.idMeal}")
            runCatching {
                toggleFavorite(meal = meal, userId = userId)
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
            roomRepository.insertItem(
                Item(
                    itemId = meal.idMeal.toInt(),
                    itemName = meal.strMeal,
                    thumbnail = meal.strMealThumb
                )
            )
            val updatedMeals = _favoriteMeals.value!!.mapNotNull {
                if (it.idMeal == meal.idMeal) {
                    val updateMeal = it.copy(isFavorite = !it.isFavorite)
                    if (updateMeal.isFavorite) {
                        Log.d(TAG, "toggleFavorite: Adding favorite for meal: ${meal.idMeal}")
                        roomRepository.insertFavorite(Favorite(userId, meal.idMeal.toInt()))
                        updateMeal
                    } else {
                        Log.d(TAG, "toggleFavorite: Removing favorite for meal: ${meal.idMeal}")
                        roomRepository.deleteFavorite(Favorite(userId, meal.idMeal.toInt()))
                        roomRepository.deleteItemById(meal.idMeal.toInt())
                        null
                    }
                } else {
                    it
                }
            }
            _favoriteMeals.value = updatedMeals
            Log.d(TAG, "toggleFavorite: Updated meals list")
        }.onFailure { exception ->
            if (exception is SocketTimeoutException) {
                handleSocketTimeoutException(exception)
            } else {
                Log.e(TAG, "toggleFavorite: Error updating favorite", exception)
            }
        }
    }

    private fun getLoggedInUserId(): Int {
        return sharedPrefHelper.getInt("user_id", -1)
    }

    private fun handleSocketTimeoutException(exception: SocketTimeoutException) {
        Log.e(TAG, "SocketTimeoutException: ${exception.message}")
    }
}
class FavouriteViewModelFactory(
    private val remoteRepository: RemoteRepository,
    private val roomRepository: RoomRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouriteViewModel(remoteRepository, roomRepository, sharedPrefHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
fun Item.toUiModel(isFavorite: Boolean) = MealUiModel(
    strMeal = itemName,
    strMealThumb = thumbnail,
    isFavorite = true,
    idMeal = itemId.toString()
)