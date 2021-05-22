package com.depromeet.muyaho.repository

import com.depromeet.muyaho.api.ApiDataModel
import com.depromeet.muyaho.api.ApiHelper
import com.depromeet.muyaho.data.AppDatabase
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.other.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.depromeet.muyaho.body.SignUpBody
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dataBase: AppDatabase
) {
    suspend fun loadStockList(stockType: String) {
        apiHelper.getStockList(stockType).body()?.let {
            dataBase.stockDao().insertAll(it.data)
        }
    }
    suspend fun loginKakao(token: String) = apiHelper.loginKaKao(token)
    suspend fun signUpKakao(body: SignUpBody) =
        apiHelper.signUpKakao(body)

    fun getStockList(type: String, name: String) = dataBase.stockDao().getStocks(type, name)

    suspend fun postMemberStock(stockId: Int, purchasePrice: Int, quantity: Int): Boolean {
        val response = apiHelper.postMemberStock(ApiDataModel.RequestPostMemberStockBody(stockId, purchasePrice, quantity))
        return response.isSuccessful
    }

    suspend fun putMemberStock(memberStockId: Int, purchasePrice: Int, quantity: Int): Boolean {
        val response = apiHelper.putMemberStock(ApiDataModel.RequestPutMemberStockBody(memberStockId, purchasePrice, quantity))
        return response.isSuccessful
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
}