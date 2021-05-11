package com.depromeet.muyaho.api

import retrofit2.Response

interface ApiHelper {
    suspend fun getEmployees(): Response<Unit>
    suspend fun getStockList(): Response<ApiDataModel.ResponseStockList>
}