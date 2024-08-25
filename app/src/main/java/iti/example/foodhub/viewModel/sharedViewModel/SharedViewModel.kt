package iti.example.foodhub.viewModel.sharedViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _mealId = MutableLiveData<String>()
    val mealId: LiveData<String> get() = _mealId

    fun setMealId(mealId: String) {
        Log.d("sharedViewModel", "setMealId: $mealId")
        _mealId.value = mealId
    }

}