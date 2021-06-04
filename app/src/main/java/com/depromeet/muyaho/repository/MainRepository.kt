package com.depromeet.muyaho.repository

import androidx.lifecycle.MutableLiveData
import com.depromeet.muyaho.api.ApiDataModel
import com.depromeet.muyaho.api.ApiHelper
import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.data.AppDatabase
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.data.MemberStockStatus
import com.depromeet.muyaho.data.StockType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
        purchasePrice: Float,
        quantity: Float,
        currencyType: String,
        purchaseTotalPrice: Float
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
        purchasePrice: Float,
        quantity: Float,
        purchaseTotalPrice: Float
    ): Boolean = apiHelper.putMemberStock(
        ApiDataModel.RequestPutMemberStockBody(
            memberStockId,
            purchasePrice,
            quantity,
            purchaseTotalPrice
        )
    ).isSuccessful

    suspend fun deleteMemberStock(memberStockId: Int): Int {
        val response = apiHelper.deleteMemberStock(memberStockId)
        return if (response.isSuccessful) {
            memberStockId
        } else {
            -1
        }
    }

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

    suspend fun getMemberStock2(stockType: String): List<MemberStock>? {
        val data = apiHelper.getMemberStock(stockType).body()?.data ?: return null
        memberStockStatusCache?.let {
            when (stockType) {
                StockType.Domestic.full_name -> {
                    it.overview.domesticStocks = data
                }
                StockType.Overseas.full_name -> {
                    it.overview.foreignStocks = data
                }
                StockType.Bitcoin.full_name -> {
                    it.overview.bitCoins = data
                }
            }
        }
        return data
    }

    suspend fun getMemberStockStatusCache(): MemberStockStatus? {
        if (memberStockStatusCache == null) {
            memberStockStatusCache = apiHelper.getMemberStockStatusHistory().body()?.data
        }
        return memberStockStatusCache
    }

    suspend fun getMemberStockStatus(): MemberStockStatus? {
        memberStockStatusCache = apiHelper.getMemberStockStatus().body()?.data
        return memberStockStatusCache
    }
}