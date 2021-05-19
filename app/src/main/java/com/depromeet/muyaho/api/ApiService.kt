package com.depromeet.muyaho.api

import com.depromeet.muyaho.data.Stock
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("employees")
    suspend fun getEmployees(): Response<Unit>

    @GET("api/v1/stock/list")
    suspend fun getStockList(@Query("type") stockType: String): Response<ApiDataModel.ResponseStockList>
}