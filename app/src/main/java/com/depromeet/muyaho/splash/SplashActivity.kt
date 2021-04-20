package com.depromeet.muyaho.splash

import android.content.Intent
import androidx.activity.viewModels
import com.depromeet.muyaho.MainActivity
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivityMainBinding, SplashViewModel, SplashViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.activity_splash
    override val vm: SplashViewModel by viewModels()

    override fun observeActionCommand(action: SplashViewModel.ViewAction) {
        when (action) {
            SplashViewModel.ViewAction.ShowMain -> showMain()
        }
    }

    private fun showMain() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}