package com.depromeet.muyaho.api

import com.depromeet.muyaho.models.LoginResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/api/v1/login/kakao")
    suspend fun loginKaKao(
        @Query("token") token: String
    ): Response<LoginResult>

    @POST("/api/v1/signup/kakao")
    suspend fun signUpKaKao(
        @Query("token") token: String,
        @Query("name") name: String,
        @Query("profileUrl") profileUrl: String
    ): Response<LoginResult>

    // 200이면 ok 409면 conflict
    @GET("/api/v1/check/name")
    suspend fun checkName(
        @Query("name") name: String
    ): Response<LoginResult>

    @GET("/api/v1/member")
    suspend fun getMember()
}