package com.depromeet.muyaho.api

import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import retrofit2.Response

interface ApiHelper {
    suspend fun loginKaKao(token: String): Response<LoginResult>
    suspend fun signUpKakao(body: SignUpBody): Response<LoginResult>
    suspend fun checkName(name: String): Response<Unit>
}