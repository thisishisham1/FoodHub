package iti.example.foodhub.data.remote.responseModel

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "favorite_items")
data class FavoriteItem(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)