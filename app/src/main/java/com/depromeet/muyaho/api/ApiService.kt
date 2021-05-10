package com.depromeet.muyaho.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("employees")
    suspend fun getEmployees(): Response<Unit>
}