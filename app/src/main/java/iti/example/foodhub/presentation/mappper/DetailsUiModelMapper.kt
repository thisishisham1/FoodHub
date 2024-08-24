package iti.example.foodhub.presentation.mappper

import iti.example.foodhub.data.remote.responseModel.Meal
import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.presentation.model.DetailsUiModel
import iti.example.foodhub.presentation.model.MealUiModel

/*class DetailsUiModelMapper {

    fun mapToUiModel(meal: Meals): DetailsUiModel {
        return DetailsUiModel(
            idMeal = meal.idMeal,
            strMeal = meal.strMeal,
            strMealImages = meal.strMealThumb,
            strDescription = meal.strInstructions,
            strYoutube = meal.strYoutube
        )
    }
}*/



fun Meals.toDetailsUi() = DetailsUiModel(
    idMeal = idMeal,
    strMeal = strMeal,
    strDescription = strInstructions,
    strYoutube = strYoutube,
    strMealImages = strMealThumb
)