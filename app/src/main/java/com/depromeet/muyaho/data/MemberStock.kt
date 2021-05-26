package com.depromeet.muyaho.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MemberStock(
    val memberStockId: Int,
    val stock: Stock,
    val quantity: String,
    val purchasePrice: String,
    val purchaseAmount: String,
    val currentPrice: String,
    val currentAmount: String,
    val earningRate: String
): Parcelable {
}