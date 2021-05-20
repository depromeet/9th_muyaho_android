package com.depromeet.muyaho.api

import com.depromeet.muyaho.models.LoginResult
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun loginKaKao(token: String): Response<LoginResult> =
        apiService.loginKaKao(token)

    override suspend fun signUpKakao(
        token: String,
        name: String,
        profileUrl: String
    ): Response<LoginResult> = apiService.signUpKaKao(token, name, profileUrl)
}