package iti.example.foodhub.viewModel.fav

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import iti.example.foodhub.data.remote.responseModel.FavoriteItem
import iti.example.foodhub.data.repository.RoomRepository
import iti.example.foodhub.viewModel.authentication.TAG
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavouriteViewModel(
    private val roomRepository: RoomRepository)
    :ViewModel(){

    private  val _favouriteMeals=MutableLiveData<List<FavoriteItem>>()
    val favouriteMeals : LiveData<List<FavoriteItem>>  = _favouriteMeals
    init {

        getFavouriteMeals(1)
    }

    fun getFavouriteMeals(userId:Int)
    {
        viewModelScope.launch {
                try {

                    val favoriteItem = roomRepository.getUserFavorites(userId)
                    Log.d("FavouriteViewModel", "getFavouriteMeals called with ID: $userId")
                    _favouriteMeals.value = favoriteItem.map { favourite ->
                        FavoriteItem(
                            id = favourite.itemId,
                            name = favourite.itemName,
                            imageUrl = "https://www.example.com/images/spaghetti.jpg"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("FavouriteViewModel", "Error fetching meal fav", e)
                }
            }
        }



    class FavouriteViewModelFactory(private val roomRepository: RoomRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavouriteViewModel(roomRepository) as T
        }
    }

}