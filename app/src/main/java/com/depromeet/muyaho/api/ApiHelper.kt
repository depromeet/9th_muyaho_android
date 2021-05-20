package com.depromeet.muyaho.api

import com.depromeet.muyaho.models.LoginResult
import retrofit2.Response

interface ApiHelper {
    suspend fun loginKaKao(token: String): Response<LoginResult>
    suspend fun signUpKakao(token: String, name: String, profileUrl: String): Response<LoginResult>
}