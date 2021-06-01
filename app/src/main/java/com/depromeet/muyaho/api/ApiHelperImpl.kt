package com.depromeet.muyaho.api

import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import com.depromeet.muyaho.util.PreferenceUtil
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

    override suspend fun checkName(name: String): Response<Unit> =
        apiService.checkName(name)

    override suspend fun getStockList(stockType: String): Response<ApiDataModel.ResponseStockList> =
        apiService.getStockList(stockType)

    override suspend fun getMemberStock(
        stockType: String
    ): Response<ApiDataModel.ResponseGetMemberStock> =
        apiService.getMemberStock(PreferenceUtil.AccessToken, stockType)

    override suspend fun postMemberStock(
        body: ApiDataModel.RequestPostMemberStockBody
    )
            : Response<ApiDataModel.ResponsePostMemberStock> =
        apiService.postMemberStock(PreferenceUtil.AccessToken, body)

    override suspend fun putMemberStock(
        body: ApiDataModel.RequestPutMemberStockBody
    )
            : Response<ApiDataModel.ResponsePutMemberStock> =
        apiService.putMemberStock(PreferenceUtil.AccessToken, body)
}