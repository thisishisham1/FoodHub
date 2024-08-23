package iti.example.foodhub.data.repository

import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.remote.source.RemoteDataSource

class HomeRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getMealsById(i: String):Meals = remoteDataSource.getMealsById(i)
    suspend fun getMealsByCategory(category: String) = remoteDataSource.getMealsByCategory(category)
}