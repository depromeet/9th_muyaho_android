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

    override suspend fun deleteMember(authorization: String): Response<Unit> =
        apiService.deleteMember(authorization)

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

    override suspend fun getMemberStockStatus(): Response<ApiDataModel.ResponseMemberStockStatus> =
        apiService.getMemberStockStatus(PreferenceUtil.AccessToken)

    override suspend fun getMemberStockStatusHistory(): Response<ApiDataModel.ResponseMemberStockStatus> =
        apiService.getMemberStockStatusHistory(PreferenceUtil.AccessToken)
}