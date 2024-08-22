package iti.example.foodhub.presentation.model

data class MealUiModel(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String,
    var isFavorite: Boolean = false,
)