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
import com.depromeet.muyaho.util.PreferenceUtil.accessToken
import retrofit2.Response
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

    override suspend fun getMember(authorization: String): Response<MemberResult> =
        apiService.getMember(authorization)

    override suspend fun getStockList(stockType: String): Response<ResponseStockList> =
        apiService.getStockList(stockType)

    override suspend fun getMemberStock(
        stockType: String
    ): Response<ResponseGetMemberStock> =
        apiService.getMemberStock(accessToken, stockType)

    override suspend fun postMemberStock(
        body: RequestPostMemberStockBody
    ): Response<ResponsePostMemberStock> =
        apiService.postMemberStock(accessToken, body)

    override suspend fun putMemberStock(
        body: RequestPutMemberStockBody
    ): Response<ResponsePutMemberStock> =
        apiService.putMemberStock(accessToken, body)
}