package com.depromeet.muyaho.repository

import com.depromeet.muyaho.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getEmployee() = apiHelper.getEmployees()
}