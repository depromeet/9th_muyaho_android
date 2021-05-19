package com.depromeet.muyaho.repository

import com.depromeet.muyaho.api.ApiHelper
import com.depromeet.muyaho.data.AppDatabase
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
}