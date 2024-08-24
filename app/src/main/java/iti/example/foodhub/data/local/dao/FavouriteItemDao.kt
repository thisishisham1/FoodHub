package iti.example.foodhub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import iti.example.foodhub.data.local.entity.Favorite
import iti.example.foodhub.data.local.entity.Item
@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM items INNER JOIN favorite_items ON items.itemId = favorite_items.itemId WHERE favorite_items.userId = :userId")
    suspend fun getUserFavorites(userId: Int): List<Item>
}