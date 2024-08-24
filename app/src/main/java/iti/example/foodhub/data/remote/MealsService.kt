package iti.example.foodhub.data.remote

import android.telecom.Call
import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.remote.responseModel.ResponseMealsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealsService {

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<ResponseMealsModel>

    @GET("lookup.php")
   suspend fun getMealsById(@Query("i") mealId: String):List<Meals>
}
