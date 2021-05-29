package com.depromeet.muyaho

import android.app.Application
import com.depromeet.muyaho.util.PreferenceUtil
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, "9d9d22a2c398477b218d7e70d58e04c5")

        // preference 초기화
        PreferenceUtil.init(applicationContext)
    }
}