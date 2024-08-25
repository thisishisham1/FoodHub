package iti.example.foodhub.data.local.source

import iti.example.foodhub.data.local.dao.Dao
import iti.example.foodhub.data.local.entity.Favorite
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.local.entity.User
import iti.example.foodhub.data.remote.source.RemoteDataSource

class LocalDataSourceImpl(private val dao: Dao) : LocalDataSource {
    override suspend fun insertItem(item: Item) {
        dao.insertItem(item)
    }

    override suspend fun getAllItems(): List<Item> {
        return dao.getAllItems()
    }

    override suspend fun registerUser(user: User) {
        dao.registerUser(user)
    }

    override suspend fun loginUser(email: String, password: String): User? {
        return dao.loginUser(email, password)
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        dao.insertFavorite(favorite)
    }

    override suspend fun getUserFavorites(userId: Int): List<Item> {
        return dao.getUserFavorites(userId)
    }
}
