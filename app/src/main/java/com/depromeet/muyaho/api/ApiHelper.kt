package com.depromeet.muyaho.api

import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import retrofit2.Response

interface ApiHelper {
	suspend fun loginKaKao(token: String): Response<LoginResult>
    suspend fun signUpKakao(body: SignUpBody): Response<LoginResult>
    
    suspend fun getStockList(stockType: String): Response<ApiDataModel.ResponseStockList>

    suspend fun getMemberStock(token: String, stockType: String)
    : Response<ApiDataModel.ResponseGetMemberStock>
    suspend fun postMemberStock(token: String, body: ApiDataModel.RequestPostMemberStockBody)
    : Response<ApiDataModel.ResponsePostMemberStock>
    suspend fun putMemberStock(token: String, body: ApiDataModel.RequestPutMemberStockBody)
    : Response<ApiDataModel.ResponsePutMemberStock>
}