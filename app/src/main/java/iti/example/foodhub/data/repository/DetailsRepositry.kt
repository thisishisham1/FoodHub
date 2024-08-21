package iti.example.foodhub.data.repository

import iti.example.foodhub.data.remote.responseModel.Meals

interface DetailsRepositry {
    suspend fun getProducts(): List<Meals>
    suspend fun getProductById(id: Int): Meals
}