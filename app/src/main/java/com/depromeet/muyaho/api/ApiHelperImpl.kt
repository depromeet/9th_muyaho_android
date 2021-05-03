package com.depromeet.muyaho.api

import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getEmployees(): Response<Unit> = apiService.getEmployees()
}