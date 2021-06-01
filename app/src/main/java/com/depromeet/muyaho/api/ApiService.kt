package com.depromeet.muyaho.api

import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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
}