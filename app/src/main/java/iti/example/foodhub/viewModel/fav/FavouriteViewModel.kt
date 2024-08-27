package iti.example.foodhub.viewModel.fav

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.local.entity.Favorite
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.remote.responseModel.ResponseDetailsModel
import iti.example.foodhub.data.repository.RoomRepository
import kotlinx.coroutines.launch


import iti.example.foodhub.data.repository.RemoteRepository
import iti.example.foodhub.presentation.model.MealUiModel
import iti.example.foodhub.sharedPref.SharedPrefHelper
import iti.example.foodhub.viewModel.Details.MealDetailsViewModel

private val TAG="FavouriteViewModel"
class FavouriteViewModel(private  val remoteRepository: RemoteRepository,
                         private val roomRepository: RoomRepository,

                         private val sharedPrefHelper: SharedPrefHelper) : ViewModel() {

    private val _favoriteMeals = MutableLiveData<List<MealUiModel>>()
    val favoriteMeals: LiveData<List<MealUiModel>> = _favoriteMeals


    init {
        getFavoriteMeals(sharedPrefHelper.getInt("user_id", -1))
    }

    fun getFavoriteMeals(userId: Int) {
        viewModelScope.launch {
            val response = remoteRepository.getMealsById(userId.toString())
            runCatching {
                val list = roomRepository.getUserFavorites(userId)
                Log.d(TAG, "User favorites: $list") // Log the list of favorite items

                val meals = mutableListOf<MealUiModel>() // Initialize a list to store fetched meals
                list.forEach { favorite ->
                    val meal = remoteRepository.getMealsById(favorite.itemId.toString())
                    meal?.let {
                        // meals.add(it)
                        Log.d(TAG, "Fetched meal: $meal")
                    }
                }

                _favoriteMeals.value = meals // Update the LiveData with the fetched meals
                Log.d(TAG, "Favorite meals updated: $meals")
            }.onSuccess {
                Log.d(TAG, "Is success ")
            }
                .onFailure { Log.d(TAG, "failure: ") }
        }
    }

    fun removeFavorite(item: Item, userId: Int) {
        viewModelScope.launch {
            roomRepository.deleteFavorite(Favorite(userId, item.itemId))
            getFavoriteMeals(userId)
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
}
























































/*class FavouriteViewModel(private val repository: RoomRepository) : ViewModel() {

    private val _favorites = MutableLiveData<List<Item>>()
    val favorites: LiveData<List<Item>> get() = _favorites

    private val _mealDetails = MutableLiveData<Meal>()
    val mealDetails: LiveData<Meal> get() = _mealDetails

    fun getUserFavorites(userId: Int) {
        viewModelScope.launch {
            _favorites.value = repository.getUserFavorites(userId)
        }
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
            getUserFavorites(favorite.userId)
        }
    }

    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
            getUserFavorites(favorite.userId)
        }
    }
/*
    fun getMealById(mealId: String) {
        viewModelScope.launch {
            val response = repository.getMealById(mealId)
            _mealDetails.value = response.meals.firstOrNull()
        }
    }*/
}



*/






















































































/*
class FavouriteViewModel(private val repository: RoomRepository) : ViewModel() {

    private val _favouriteItems = MutableLiveData<List<MealUiModel>>()
    val favouriteItems: LiveData<List<MealUiModel>> get() = _favouriteItems

    fun getUserFavorites(userId: Int) {
        viewModelScope.launch {
            val items = repository.getUserFavorites(userId)
            _favouriteItems.value = items.map {
                MealUiModel(
                    strMeal = it.itemName,
                    strMealThumb = "",
                    idMeal = it.itemId.toString(),
                    isFavorite = true
                )
            }
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
            getUserFavorites(favorite.userId)
        }
    }
}
class FavouriteViewModelFactory(private val roomRepository: RoomRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouriteViewModel(roomRepository) as T
    }
}














*/

































































