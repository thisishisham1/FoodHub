package iti.example.foodhub.data.remote.source

import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.remote.responseModel.ResponseMealsModel
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getMealsByCategory(category: String): Response<ResponseMealsModel>
    suspend fun getMealsById(i: String):List< Meals>
}