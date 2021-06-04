package com.depromeet.muyaho.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberStockStatus(
    val todayProfitOrLose: String,
    val finalAsset: String,
    val seedAmount: String,
    val finalProfitOrLoseRate: String,
    var overview: MemberStockStatusOverview
): Parcelable
