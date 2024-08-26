package iti.example.foodhub.data.repository

import iti.example.foodhub.data.remote.source.RemoteDataSource

class RemoteRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getMealsById(i: String) = remoteDataSource.getMealsById(i)

    suspend fun getMealsByCategory(category: String) = remoteDataSource.getMealsByCategory(category)

    suspend fun getMealsBySearch(query: String) = remoteDataSource.getMealsBySearch(query)
}