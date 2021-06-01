package com.depromeet.muyaho.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MemberStock(
    val memberStockId: Int,
    val stock: Stock,
    val purchase: Purchase,
    val current: Current,
    val quantity: String,
    val currencyType: String,
    val profitOrLoseRate: String
): Parcelable {
}