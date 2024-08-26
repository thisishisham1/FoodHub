package iti.example.foodhub.viewModel.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.local.entity.Favorite
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.local.entity.User
import iti.example.foodhub.data.repository.HomeRepository
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.presentation.auth.AuthActivity
import iti.example.foodhub.presentation.main.MainActivity
import iti.example.foodhub.presentation.mappper.toUiModel
import iti.example.foodhub.presentation.model.MealUiModel
import iti.example.foodhub.sharedPref.SharedPrefHelper
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.SocketTimeoutException

private const val TAG = "HomeViewModel"

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val roomRepository: RoomRepository, private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {
    private var _meals = MutableLiveData<List<MealUiModel>>()
    val meals: LiveData<List<MealUiModel>> = _meals

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> = _userInfo

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        getUserInfo(1)
    }

    fun getMealsByCategory(category: String) {
        Log.d(TAG, "getMealsByCategory: $category")
        viewModelScope.launch {
            runBlocking {
                runCatching {
                    val response = homeRepository.getMealsByCategory(category)
                    if (response.isSuccessful) {
                        val mealList =
                            response.body()?.meals?.map { it.toUiModel(false) } ?: emptyList()
                        val favoriteItems = roomRepository.getUserFavorites(1)
                        val updatedMeals = mealList.map { meal ->
                            meal.copy(isFavorite = favoriteItems.any { it.itemId == meal.idMeal.toInt() })
                        }
                        _meals.value = updatedMeals
                    } else {
                        Log.d(TAG, "getMealsByCategory: failed")
                    }
                }.onFailure { exception ->
                    if (exception is SocketTimeoutException) {
                        handleSocketTimeoutException(exception)
                    } else {
                        Log.e(TAG, "getMealsByCategory: Error", exception)
                    }
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

    private fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            runCatching {
                _userInfo.value = roomRepository.getUserInfo(userId)
                Log.d(TAG, "getUserInfo: ${_userInfo.value}")
            }
                .onSuccess { Log.d(TAG, "getUserInfo: done") }
                .onFailure { exception ->
                    if (exception is SocketTimeoutException) {
                        handleSocketTimeoutException(exception)
                    } else {
                        Log.e(TAG, "getUserInfo: Error getting user info", exception)
                    }
                }
        }
    }

    fun signOut(context: Context) {
        sharedPrefHelper.clear()
        val intent = Intent(context, AuthActivity::class.java)
        context.startActivity(intent)
        (context as? MainActivity)?.finish()
    }

    private fun handleSocketTimeoutException(exception: SocketTimeoutException) {
        Log.e(TAG, "SocketTimeoutException: ${exception.message}")
        _error.value = "Network timeout. Please try again later."
    }
}

class HomeViewModelFactory(
    private val homeRepository: HomeRepository,
    private val roomRepository: RoomRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository, roomRepository, sharedPrefHelper) as T
    }
}