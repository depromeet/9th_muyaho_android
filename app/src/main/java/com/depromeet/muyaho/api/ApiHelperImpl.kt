package com.depromeet.muyaho.api

import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.models.LoginResult
import com.depromeet.muyaho.models.MemberResult
import com.depromeet.muyaho.util.PreferenceUtil
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
        apiService.getMemberStock(PreferenceUtil.accessToken, stockType)

    override suspend fun postMemberStock(
        body: ApiDataModel.RequestPostMemberStockBody
    )
            : Response<ApiDataModel.ResponsePostMemberStock> =
        apiService.postMemberStock(PreferenceUtil.accessToken, body)

    override suspend fun putMemberStock(
        body: ApiDataModel.RequestPutMemberStockBody
    )
            : Response<ApiDataModel.ResponsePutMemberStock> =
        apiService.putMemberStock(PreferenceUtil.accessToken, body)

    override suspend fun deleteMemberStock(
        memberStockId: Int
    ): Response<ApiDataModel.ResponseDeleteMemberStock> =
        apiService.deleteMemberStock(PreferenceUtil.accessToken, memberStockId)

    override suspend fun getMemberStockStatus(): Response<ApiDataModel.ResponseMemberStockStatus> =
        apiService.getMemberStockStatus(PreferenceUtil.accessToken)

    override suspend fun getMemberStockStatusHistory(): Response<ApiDataModel.ResponseMemberStockStatus> =
        apiService.getMemberStockStatusHistory(PreferenceUtil.accessToken)

    override suspend fun getExchangeRate(): Response<ApiDataModel.ResponseExchangeRate> =
        apiService.getExchangeRate()
}