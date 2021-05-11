package com.depromeet.muyaho.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks")
data class Stock(
    @PrimaryKey val id: Int,
    val code: String,
    val name: String,
    val type: String
)
