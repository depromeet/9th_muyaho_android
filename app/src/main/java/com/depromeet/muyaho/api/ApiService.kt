package com.depromeet.muyaho.api

import com.depromeet.muyaho.data.Stock
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("employees")
    suspend fun getEmployees(): Response<Unit>

    @GET("api/v1/stock/list?type=BITCOIN")
    suspend fun getStockList(): Response<ApiDataModel.ResponseStockList>
}