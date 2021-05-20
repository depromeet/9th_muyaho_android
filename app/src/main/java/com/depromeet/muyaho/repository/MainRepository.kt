package com.depromeet.muyaho.repository

import com.depromeet.muyaho.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun loginKakao(token: String) = apiHelper.loginKaKao(token)
    suspend fun signUpKakao(token: String, name: String, profileUrl: String) =
        apiHelper.signUpKakao(token, name, profileUrl)
}