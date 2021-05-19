package com.depromeet.muyaho.api

import com.depromeet.muyaho.data.Stock

object ApiDataModel {

    data class ResponseStockList(
        val code: String,
        val message: String,
        val data: List<Stock>
    )

    data class ResponsePostMemberStock(
        val code: String,
        val message: String,
        val data: ResponseMemberStockData
    )

    data class ResponseMemberStockData(
        val memberStockId: Int,
        val stock: Stock,
        val purchasePrice: String,
        val quantity: String,
        val purchaseAmount: String
    )

    data class RequestPostMemberStockBody(
        val stockId: Int,
        val purchasePrice: Int,
        val quantity: Int
    )
}