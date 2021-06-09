package com.depromeet.muyaho.ui.splash

import android.content.Intent
import androidx.activity.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivityMainBinding
import com.depromeet.muyaho.ui.MainActivity
import com.depromeet.muyaho.ui.sign.SignActivity
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivityMainBinding, SplashViewModel, SplashViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.activity_splash
    override val vm: SplashViewModel by viewModels()

    override fun observeActionCommand(action: SplashViewModel.ViewAction) {
        when (action) {
            SplashViewModel.ViewAction.CheckAutoLogin -> checkAutoLogin()
        }
    }

    private fun checkAutoLogin() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) goToSign()
            else if (tokenInfo != null) showMain()
        }
    }

    private fun showMain() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToSign() {
        val intent = Intent(this@SplashActivity, SignActivity::class.java)
        startActivity(intent)
        finish()
    }
}