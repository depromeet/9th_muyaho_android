package com.depromeet.muyaho

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.Default) {
            // 초기화 작업은 여기에서 진행
            delay(2000)

            withContext(Dispatchers.Main) {
                val intent = Intent(this@LaunchSplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}