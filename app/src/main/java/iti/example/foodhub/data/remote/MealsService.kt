package iti.example.foodhub.data.remote

import iti.example.foodhub.data.remote.responseModel.ResponseMealsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsService {

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<ResponseMealsModel>
}