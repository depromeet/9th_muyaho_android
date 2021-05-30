package com.depromeet.muyaho.api

import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.data.MemberStockStatus
import com.depromeet.muyaho.data.Stock

object ApiDataModel {

    data class ResponseStockList(
        val code: String,
        val message: String,
        val data: List<Stock>
    )

    data class ResponseGetMemberStock(
        val code: String,
        val message: String,
        val data: List<MemberStock>
    )

    data class ResponsePostMemberStock(
        val code: String,
        val message: String,
        val data: ResponseMemberStockData
    )

    data class ResponsePutMemberStock(
        val code: String,
        val message: String,
        val data: ResponseMemberStockData
    )

    data class ResponseMemberStockData(
        val memberStockId: Int,
        val stock: Stock,
        val currencyType: String,
        val quantity: String,
        val purchasePrice: String,
        val purchaseAmount: String,
        val purchaseAmountInWon: String
    )

    data class RequestPostMemberStockBody(
        val stockId: Int,
        val purchasePrice: Int,
        val quantity: Int,
        val currencyType: String,
        val purchaseTotalPrice: Int
    )

    data class RequestPutMemberStockBody(
        val memberStockId: Int,
        val purchasePrice: Int,
        val quantity: Int,
        val purchaseTotalPrice: Int
    )

    data class ResponseMemberStockStatus(
        val code: String,
        val message: String,
        val data: MemberStockStatus
    )
}