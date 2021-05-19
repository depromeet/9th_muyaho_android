package com.depromeet.muyaho.data

enum class StockType(
    val full_name: String
) {
    Domestic("DOMESTIC_STOCK"),
    Overseas("OVERSEAS_STOCK"),
    Bitcoin("BITCOIN")
}