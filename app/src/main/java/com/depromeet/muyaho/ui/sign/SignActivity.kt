package com.depromeet.muyaho.ui.sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivitySignBinding
import com.depromeet.muyaho.ui.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : BaseActivity<ActivitySignBinding, SignViewModel, SignViewModel.ViewAction>() {
    override val layoutResId: Int = R.layout.activity_sign
    override val vm: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm
    }

    override fun observeActionCommand(action: SignViewModel.ViewAction) {
        when (action) {
            SignViewModel.ViewAction.LoginKakao -> loginWithKakao()
            SignViewModel.ViewAction.GoMain -> goToMain()
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginWithKakao() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error == null) token?.let { getUserInfo(it) } ?: Log.e(TAG, "token is null")
                else Log.d(TAG, "error: $error")
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                if (error == null) token?.let { getUserInfo(it) } ?: Log.e(TAG, "token is null")
                else Log.d(TAG, "error: $error")
            }
        }
    }

    private fun getUserInfo(token: OAuthToken) {
        UserApiClient.instance.me { user, error ->
            if (error == null) vm.loginWithKakao(token, user?.kakaoAccount?.profile)
            else Log.d(TAG, "error: $error")
        }
    }
}