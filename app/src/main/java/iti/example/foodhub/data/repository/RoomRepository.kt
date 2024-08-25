package iti.example.foodhub.data.repository

import iti.example.foodhub.data.local.entity.Favorite
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.local.entity.User
import iti.example.foodhub.data.local.source.LocalDataSource

class RoomRepository(private val localDataSource: LocalDataSource) {
    suspend fun insertItem(item: Item) = localDataSource.insertItem(item)
    suspend fun getAllItems() = localDataSource.getAllItems()
    suspend fun registerUser(user: User) = localDataSource.registerUser(user)
    suspend fun loginUser(email: String, password: String) = localDataSource.loginUser(email, password)
    suspend fun deleteItemById(itemId: Int) = localDataSource.deleteItemById(itemId)
    suspend fun insertFavorite(favorite: Favorite) = localDataSource.insertFavorite(favorite)
    suspend fun getUserFavorites(userId: Int) = localDataSource.getItemsInFavorite(userId)
    suspend fun deleteFavorite(favorite: Favorite) = localDataSource.deleteFavorite(favorite)
}