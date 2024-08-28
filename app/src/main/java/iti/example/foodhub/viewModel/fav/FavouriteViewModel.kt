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

class FavouriteViewModel() : ViewModel() {

}