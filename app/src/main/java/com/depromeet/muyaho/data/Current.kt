package com.depromeet.muyaho.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Current(
    val won: CurrentSub,
    val dollar: CurrentSub
): Parcelable
