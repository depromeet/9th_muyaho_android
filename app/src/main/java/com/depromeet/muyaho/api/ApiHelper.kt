package com.depromeet.muyaho.api

import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import retrofit2.Response

interface ApiHelper {
    suspend fun loginKaKao(token: String): Response<LoginResult>
    suspend fun signUpKakao(body: SignUpBody): Response<LoginResult>

    suspend fun getStockList(stockType: String): Response<ApiDataModel.ResponseStockList>

    suspend fun getMemberStock(stockType: String)
            : Response<ApiDataModel.ResponseGetMemberStock>

    suspend fun postMemberStock(body: ApiDataModel.RequestPostMemberStockBody)
            : Response<ApiDataModel.ResponsePostMemberStock>

    suspend fun putMemberStock(body: ApiDataModel.RequestPutMemberStockBody)
            : Response<ApiDataModel.ResponsePutMemberStock>

    suspend fun getMemberStockStatus()
            : Response<ApiDataModel.ResponseMemberStockStatus>

    suspend fun getMemberStockStatusHistory()
            : Response<ApiDataModel.ResponseMemberStockStatus>
}