package iti.example.foodhub.data.remote.responseModel

data class ResponseMealsModel(
    val meals: List<Meal>
)

data class Meal(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
)