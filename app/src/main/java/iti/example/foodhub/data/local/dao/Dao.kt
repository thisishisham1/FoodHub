package iti.example.foodhub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import iti.example.foodhub.data.local.entity.Favorite
import iti.example.foodhub.data.local.entity.Item
import iti.example.foodhub.data.local.entity.User

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<Item>

    @Insert
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE FROM items WHERE itemId = :itemId")
    suspend fun deleteItemById(itemId: Int)

    @Query("SELECT items.* FROM favorite_items JOIN items ON favorite_items.itemId = items.itemId WHERE favorite_items.userId = :userId")
    suspend fun getItemsInFavorite(userId: Int): List<Item>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserInfo(userId: Int): User
}