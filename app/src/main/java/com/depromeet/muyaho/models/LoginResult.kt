package com.depromeet.muyaho.models

data class LoginResult(
    val code: String,
    val message: String,
    val data: LoginData
)