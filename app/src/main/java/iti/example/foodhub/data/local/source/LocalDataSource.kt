package iti.example.foodhub.data.local.source

import iti.example.foodhub.data.local.entity.Favorite
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.local.entity.User

interface LocalDataSource {
    suspend fun insertItem(item: Item)
    suspend fun getAllItems(): List<Item>
    suspend fun registerUser(user: User)
    suspend fun loginUser(email: String, password: String): User?
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun getUserFavorites(userId: Int): List<Item>
}