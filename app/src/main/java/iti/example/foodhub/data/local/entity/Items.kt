package iti.example.foodhub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    val thumbnail: String,
    val itemName: String,
    @PrimaryKey val itemId: Int,
)