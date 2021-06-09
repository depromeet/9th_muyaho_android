package com.depromeet.muyaho.util

import java.math.BigDecimal
import java.text.NumberFormat

object NumberFormatUtil {

    fun numWithDollar(num: BigDecimal): String {
        return "${numWithComma(num)} USD"
    }

    fun numWithCount(num: BigDecimal): String {
        return "${numWithComma(num)} 개"
    }

    fun numWithComma(num: BigDecimal): String {
        return NumberFormat.getInstance().format(num)
    }
}