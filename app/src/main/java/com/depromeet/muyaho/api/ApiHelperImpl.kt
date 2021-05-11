package com.depromeet.muyaho.api

import com.depromeet.muyaho.data.Stock
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getEmployees(): Response<Unit> = apiService.getEmployees()

    override suspend fun getStockList(): Response<ApiDataModel.ResponseStockList> = apiService.getStockList()
}