package iti.example.foodhub.data.remote.source

import iti.example.foodhub.data.remote.MealsService
import iti.example.foodhub.data.remote.responseModel.ResponseMealsModel
import retrofit2.Response

class RemoteDataSourceImpl(private val mealsService: MealsService) : RemoteDataSource{
    override suspend fun getMealsByCategory(category: String): Response<ResponseMealsModel> {
        return mealsService.getMealsByCategory(category)
    }
}