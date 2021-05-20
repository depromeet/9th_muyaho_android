package com.depromeet.muyaho.ui.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivitySignBinding
import com.depromeet.muyaho.ui.MainActivity
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
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = vm.callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = vm.callback)
        }
    }
}