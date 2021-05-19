package com.depromeet.muyaho.api

import retrofit2.Response

interface ApiHelper {
    suspend fun getStockList(stockType: String): Response<ApiDataModel.ResponseStockList>

    suspend fun getMemberStock(token: String, stockType: String)
    : Response<ApiDataModel.ResponseGetMemberStock>
    suspend fun postMemberStock(token: String, body: ApiDataModel.RequestPostMemberStockBody)
    : Response<ApiDataModel.ResponsePostMemberStock>
    suspend fun putMemberStock(token: String, body: ApiDataModel.RequestPutMemberStockBody)
    : Response<ApiDataModel.ResponsePutMemberStock>
}