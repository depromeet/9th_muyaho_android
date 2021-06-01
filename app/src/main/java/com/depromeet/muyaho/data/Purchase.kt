package com.depromeet.muyaho.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Purchase(
    val unitPrice: String,
    val amount: String,
    val amountInWon: String
): Parcelable
