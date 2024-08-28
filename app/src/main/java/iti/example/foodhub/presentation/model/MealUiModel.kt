package iti.example.foodhub.presentation.model

import iti.example.foodhub.data.remote.responseModel.Meal

data class MealUiModel(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String,
    var isFavorite: Boolean = false,
)