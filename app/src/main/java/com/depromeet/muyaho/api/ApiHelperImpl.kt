package com.depromeet.muyaho.api

import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
	override suspend fun loginKaKao(token: String): Response<LoginResult> =
        apiService.loginKaKao(token)

    override suspend fun signUpKakao(body: SignUpBody): Response<LoginResult> =
        apiService.signUpKaKao(body)

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