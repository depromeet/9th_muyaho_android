package com.depromeet.muyaho.repository

import com.depromeet.muyaho.api.ApiDataModel
import com.depromeet.muyaho.api.ApiHelper
import com.depromeet.muyaho.data.AppDatabase
import com.depromeet.muyaho.other.Constants
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dataBase: AppDatabase
) {
    suspend fun getEmployee() = apiHelper.getEmployees()

    suspend fun loadStockList(stockType: String) {
        apiHelper.getStockList(stockType).body()?.let {
            dataBase.stockDao().insertAll(it.data)
        }
    }

    fun getStockList(type: String, name: String) = dataBase.stockDao().getStocks(type, name)

    suspend fun postMemberStock(stockId: Int, purchasePrice: Int, quantity: Int): Boolean {
        val response = apiHelper.postMemberStock("Bearer ${Constants.TEST_TOKEN}", ApiDataModel.RequestPostMemberStockBody(stockId, purchasePrice, quantity))
        return response.isSuccessful
    }
}