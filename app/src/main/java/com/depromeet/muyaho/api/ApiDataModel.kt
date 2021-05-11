package com.depromeet.muyaho.api

import com.depromeet.muyaho.data.Stock

object ApiDataModel {

    data class ResponseStockList(
        val code: String,
        val message: String,
        val data: List<Stock>
    )
}