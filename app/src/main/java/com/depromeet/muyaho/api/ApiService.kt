package com.depromeet.muyaho.api

import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import com.depromeet.muyaho.models.MemberResult
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
        @Query("name") name: String
    ): Response<Unit>

    @GET("/api/v1/member")
    suspend fun getMember(
        @Header("Authorization") token: String
    ): Response<MemberResult>

    @DELETE("/api/v1/member")
    suspend fun deleteMember(
        @Header("Authorization") token: String
    ): Response<Unit>

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

    @DELETE("/api/v1/member/stock")
    @Headers("Content-Type: application/json")
    suspend fun deleteMemberStock(
        @Header("Authorization") token: String,
        @Query("memberStockId") memberStockId: Int
    ): Response<ApiDataModel.ResponseDeleteMemberStock>

    @GET("/api/v1/member/stock/status")
    suspend fun getMemberStockStatus(
        @Header("Authorization") token: String
    ): Response<ApiDataModel.ResponseMemberStockStatus>

    @GET("/api/v1/member/stock/status/history")
    suspend fun getMemberStockStatusHistory(
        @Header("Authorization") token: String
    ): Response<ApiDataModel.ResponseMemberStockStatus>
}