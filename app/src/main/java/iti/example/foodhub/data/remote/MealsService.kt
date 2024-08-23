package iti.example.foodhub.data.remote

import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.remote.responseModel.ResponseMealsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealsService {

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<ResponseMealsModel>
    //@GET("products")
    //suspend fun getMeals():ResponseMealsModel

    @GET("products/{id}")
    suspend fun getMealsById(@Path("id") id: Int): Meals
}