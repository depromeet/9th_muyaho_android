package com.depromeet.muyaho.api

import com.depromeet.muyaho.data.Stock
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("api/v1/stock/list")
    suspend fun getStockList(@Query("type") stockType: String): Response<ApiDataModel.ResponseStockList>

    @GET("/api/v1/member/stock")
    suspend fun getMemberStock(@Header("Authorization") token: String,
                               @Query("type") stockType: String)
            : Response<ApiDataModel.ResponseGetMemberStock>

    @POST("/api/v1/member/stock")
    @Headers("Content-Type: application/json")
    suspend fun postMemberStock(@Header("Authorization") token: String,
                                @Body body: ApiDataModel.RequestPostMemberStockBody)
    : Response<ApiDataModel.ResponsePostMemberStock>

    @PUT("/api/v1/member/stock")
    @Headers("Content-Type: application/json")
    suspend fun putMemberStock(@Header("Authorization") token: String,
                                @Body body: ApiDataModel.RequestPutMemberStockBody)
    : Response<ApiDataModel.ResponsePutMemberStock>
}