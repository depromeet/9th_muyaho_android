package com.depromeet.muyaho.repository

import com.depromeet.muyaho.api.ApiHelper
import com.depromeet.muyaho.body.SignUpBody
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun loginKakao(token: String) = apiHelper.loginKaKao(token)
    suspend fun signUpKakao(body: SignUpBody) =
        apiHelper.signUpKakao(body)
}