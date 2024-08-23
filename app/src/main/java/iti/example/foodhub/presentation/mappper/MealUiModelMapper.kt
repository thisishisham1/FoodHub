package iti.example.foodhub.presentation.mappper

import iti.example.foodhub.data.remote.responseModel.Meal
import iti.example.foodhub.presentation.model.MealUiModel

fun Meal.toUiModel(isFavorite: Boolean) = MealUiModel(
    strMeal = strMeal,
    strMealThumb = strMealThumb,
    idMeal = idMeal,
    isFavorite = isFavorite,

)