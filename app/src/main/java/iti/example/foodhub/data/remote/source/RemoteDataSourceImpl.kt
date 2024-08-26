package iti.example.foodhub.data.remote.source

import iti.example.foodhub.data.remote.MealsService
import iti.example.foodhub.data.remote.responseModel.Meals
import iti.example.foodhub.data.remote.responseModel.ResponseDetailsModel
import iti.example.foodhub.data.remote.responseModel.ResponseMealsModel
import retrofit2.Response

class RemoteDataSourceImpl(private val mealsService: MealsService) : RemoteDataSource {

    override suspend fun getMealsByCategory(category: String): Response<ResponseMealsModel> {
        return mealsService.getMealsByCategory(category)
    }

    override suspend fun getMealsById(i: String): ResponseDetailsModel {
        return mealsService.getMealsById(i)
    }

    override suspend fun getMealsBySearch(query: String): Response<ResponseMealsModel> {
        return mealsService.getMealsBySearch(query)
    }

}