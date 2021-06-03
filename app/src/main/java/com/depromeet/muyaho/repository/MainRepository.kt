package com.depromeet.muyaho.repository

import com.depromeet.muyaho.api.ApiDataModel
import com.depromeet.muyaho.api.ApiHelper
import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.data.AppDatabase
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.data.MemberStockStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dataBase: AppDatabase
) {
    private var memberStockStatusCache: MemberStockStatus? = null

    suspend fun loadStockList(stockType: String) {
        apiHelper.getStockList(stockType).body()?.let {
            dataBase.stockDao().insertAll(it.data)
        }
    }

    suspend fun loginKakao(token: String) = apiHelper.loginKaKao(token)
    suspend fun signUpKakao(body: SignUpBody) = apiHelper.signUpKakao(body)

    suspend fun checkNickName(name: String) = apiHelper.checkName(name)
    suspend fun getMember(authorization: String) = apiHelper.getMember(authorization)
    suspend fun deleteMember(authorization: String) = apiHelper.deleteMember(authorization)

    fun getStockList(type: String, name: String) = dataBase.stockDao().getStocks(type, name)

    suspend fun postMemberStock(
        stockId: Int,
        purchasePrice: Int,
        quantity: Int,
        currencyType: String,
        purchaseTotalPrice: Int
    ): Boolean = apiHelper.postMemberStock(
        ApiDataModel.RequestPostMemberStockBody(
            stockId,
            purchasePrice,
            quantity,
            currencyType,
            purchaseTotalPrice
        )
    ).isSuccessful

    suspend fun putMemberStock(
        memberStockId: Int,
        purchasePrice: Int,
        quantity: Int,
        purchaseTotalPrice: Int
    ): Boolean = apiHelper.putMemberStock(
        ApiDataModel.RequestPutMemberStockBody(
            memberStockId,
            purchasePrice,
            quantity,
            purchaseTotalPrice
        )
    ).isSuccessful

    suspend fun getMemberStock(stockType: String): Flow<List<MemberStock>> = flow {
        val result = apiHelper.getMemberStock(stockType)
        if (result.isSuccessful) {
            if (result.body() == null) {
                emit(emptyList<MemberStock>())
            } else {
                emit(result.body()!!.data)
            }
        } else {
            emit(emptyList<MemberStock>())
        }
    }

    suspend fun getMemberStockStatusCache(): Flow<MemberStockStatus> = flow {
        if (memberStockStatusCache == null) {
            memberStockStatusCache = apiHelper.getMemberStockStatusHistory().body()?.data
        }

        memberStockStatusCache?.also {
            emit(it)
        }
    }

    suspend fun getMemberStockStatus(): Flow<MemberStockStatus> = flow {
        memberStockStatusCache = apiHelper.getMemberStockStatus().body()?.data

        memberStockStatusCache?.also {
            emit(it)
        }
    }
}