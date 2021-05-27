package com.depromeet.muyaho.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentSub(
    val unitPrice: String,
    val amountPrice: String
): Parcelable
