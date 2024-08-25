package iti.example.foodhub.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_items",
    primaryKeys = ["userId", "itemId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
        ),
        ForeignKey(
            entity = Item::class,
            parentColumns = ["itemId"],
            childColumns = ["itemId"],
        )
    ],
    indices = [Index(value = ["userId"]), Index(value = ["itemId"])]
)
data class Favorite(
    val userId: Int,
    val itemId: Int,
)