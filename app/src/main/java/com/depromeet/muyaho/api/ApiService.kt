package com.depromeet.muyaho.api

import com.depromeet.muyaho.data.Stock
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("employees")
    suspend fun getEmployees(): Response<Unit>

    @GET("api/v1/stock/list")
    suspend fun getStockList(@Query("type") stockType: String): Response<ApiDataModel.ResponseStockList>

    @POST("/api/v1/member/stock")
    @Headers("Content-Type: application/json")
    suspend fun postMemberStock(@Header("Authorization") token: String,
                                @Body body: ApiDataModel.RequestPostMemberStockBody)
    : Response<ApiDataModel.ResponsePostMemberStock>
}