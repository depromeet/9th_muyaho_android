package com.depromeet.muyaho.util

import java.text.NumberFormat

object NumberFormatUtil {

    fun numWithDollar(num: Float): String {
        return "${numWithComma(num)} USD"
    }

    fun numWithCount(num: Float): String {
        return "${numWithComma(num)} 개"
    }

    fun numWithComma(num: Float): String {
        return NumberFormat.getInstance().format(num)
    }
}