package iti.example.foodhub.data.remote.retrofit

import iti.example.foodhub.data.remote.MealsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val mealsService: MealsService = retrofit.create(MealsService::class.java)
}