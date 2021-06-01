package com.depromeet.muyaho.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "stocks")
data class Stock(
    @PrimaryKey val id: Int,
    val code: String,
    val name: String,
    val type: String
): Parcelable
