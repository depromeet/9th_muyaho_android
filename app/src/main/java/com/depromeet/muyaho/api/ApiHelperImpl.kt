package com.depromeet.muyaho.api

import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getStockList(stockType: String): Response<ApiDataModel.ResponseStockList> = apiService.getStockList(stockType)

    override suspend fun getMemberStock(
        token: String,
        stockType: String
    ): Response<ApiDataModel.ResponseGetMemberStock>
    = apiService.getMemberStock(token, stockType)

    override suspend fun postMemberStock(
        token: String,
        body: ApiDataModel.RequestPostMemberStockBody)
    : Response<ApiDataModel.ResponsePostMemberStock>
    = apiService.postMemberStock(token, body)

    override suspend fun putMemberStock(
        token: String,
        body: ApiDataModel.RequestPutMemberStockBody)
    : Response<ApiDataModel.ResponsePutMemberStock>
    = apiService.putMemberStock(token, body)
}