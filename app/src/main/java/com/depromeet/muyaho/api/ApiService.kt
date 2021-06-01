package com.depromeet.muyaho.api

import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/api/v1/login/kakao")
    suspend fun loginKaKao(
        @Body token: String
    ): Response<LoginResult>

    @POST("/api/v1/signup/kakao")
    suspend fun signUpKaKao(
        @Body body: SignUpBody
    ): Response<LoginResult>

    // 200이면 ok 409면 conflict
    @GET("/api/v1/check/name")
    suspend fun checkName(
        @Body name: String
    ): Response<Unit>

    @GET("/api/v1/member")
    suspend fun getMember()

    @GET("api/v1/stock/list")
    suspend fun getStockList(@Query("type") stockType: String): Response<ApiDataModel.ResponseStockList>

    @GET("/api/v1/member/stock")
    suspend fun getMemberStock(
        @Header("Authorization") token: String,
        @Query("type") stockType: String
    ): Response<ApiDataModel.ResponseGetMemberStock>

    @POST("/api/v1/member/stock")
    @Headers("Content-Type: application/json")
    suspend fun postMemberStock(
        @Header("Authorization") token: String,
        @Body body: ApiDataModel.RequestPostMemberStockBody
    ): Response<ApiDataModel.ResponsePostMemberStock>

    @PUT("/api/v1/member/stock")
    @Headers("Content-Type: application/json")
    suspend fun putMemberStock(
        @Header("Authorization") token: String,
        @Body body: ApiDataModel.RequestPutMemberStockBody
    ): Response<ApiDataModel.ResponsePutMemberStock>
}