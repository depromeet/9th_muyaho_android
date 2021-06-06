package com.depromeet.muyaho.other

import java.math.BigDecimal

object Constants {
    const val BASE_URL = "http://muyaho-api-alb-302156047.ap-northeast-2.elb.amazonaws.com/"
    const val DATABASE_NAME = "muyaho-db"

    const val TEST_SESSION_ID = "7b126a9c-3245-40a2-bfab-3f0ccb846ee3"

    const val CODE_200_OK = 200
    const val CODE_400_BAD_REQUEST = 400
    const val CODE_404_NOT_FOUND = 404
    const val CODE_409_CONFLICT = 409

    val DEFAULT_OVERSEAS_EXCHANGE_RATE = 1200.toBigDecimal()
    var OVERSEAS_EXCHANGE_RATE: BigDecimal = DEFAULT_OVERSEAS_EXCHANGE_RATE

    val YOUNGCHAN_MENT_NONE = arrayOf(
        arrayOf("zzzz...", "zzzzzz...")
    )

    val YOUNGCHAN_MENT_PLUS = arrayOf(
        arrayOf("화려한 빨간불이", "나를 감싸네..."),
        arrayOf("도지타고", "화성 갈꾸니깐"),
        arrayOf("늦으면", "어시없다")
   )

    val YOUNGCHAN_MENT_MINUS = arrayOf(
        arrayOf("우린...", "화성갈꺼니까..."),
        arrayOf("영...차....", "영.......차......"),
        arrayOf("존...버는", "승ㄹ...ㅣㅎ")
    )
}