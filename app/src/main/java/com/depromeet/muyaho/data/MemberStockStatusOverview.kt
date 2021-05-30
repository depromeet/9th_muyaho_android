package com.depromeet.muyaho.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberStockStatusOverview(
    val bitCoins: List<MemberStock>,
    val domesticStocks: List<MemberStock>,
    val foreignStocks: List<MemberStock>
): Parcelable
