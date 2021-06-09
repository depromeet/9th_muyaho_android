package com.depromeet.muyaho.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberStockStatusOverview(
    var bitCoins: List<MemberStock>,
    var domesticStocks: List<MemberStock>,
    var foreignStocks: List<MemberStock>
): Parcelable
