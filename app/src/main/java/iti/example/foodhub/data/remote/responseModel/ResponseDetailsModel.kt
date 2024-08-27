package iti.example.foodhub.data.remote.responseModel

import iti.example.foodhub.data.remote.MealsService

data class ResponseDetailsModel(
    val meals:List<Meals>
)
data class Meals(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strYoutube: String,
    val strInstructions  : String,


)











