package com.depromeet.muyaho.api

import com.depromeet.muyaho.api.ApiDataModel.RequestPostMemberStockBody
import com.depromeet.muyaho.api.ApiDataModel.RequestPutMemberStockBody
import com.depromeet.muyaho.api.ApiDataModel.ResponseGetMemberStock
import com.depromeet.muyaho.api.ApiDataModel.ResponsePostMemberStock
import com.depromeet.muyaho.api.ApiDataModel.ResponsePutMemberStock
import com.depromeet.muyaho.api.ApiDataModel.ResponseStockList
import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import com.depromeet.muyaho.models.MemberResult
import retrofit2.Response

interface ApiHelper {
    suspend fun loginKaKao(token: String): Response<LoginResult>
    suspend fun signUpKakao(body: SignUpBody): Response<LoginResult>

    suspend fun checkName(name: String): Response<Unit>

    suspend fun getMember(authorization: String): Response<MemberResult>

    suspend fun getStockList(stockType: String): Response<ResponseStockList>
    suspend fun getMemberStock(stockType: String): Response<ResponseGetMemberStock>
    suspend fun postMemberStock(body: RequestPostMemberStockBody): Response<ResponsePostMemberStock>
    suspend fun putMemberStock(body: RequestPutMemberStockBody): Response<ResponsePutMemberStock>
}