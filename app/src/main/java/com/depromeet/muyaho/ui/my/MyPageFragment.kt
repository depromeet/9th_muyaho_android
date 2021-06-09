package com.depromeet.muyaho.ui.my

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentMypageBinding
import com.depromeet.muyaho.ui.my.MyPageViewModel.ViewAction.ShowError
import com.depromeet.muyaho.ui.splash.SplashActivity
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMypageBinding, MyPageViewModel, MyPageViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_mypage
    override val vm: MyPageViewModel by viewModels()

    override fun observeActionCommand(action: MyPageViewModel.ViewAction) {
        when (action) {
            MyPageViewModel.ViewAction.GoSplash -> goToSplash()
            MyPageViewModel.ViewAction.Logout -> logout()
            is ShowError -> showError(action)
        }
    }

    private fun showError(action: ShowError) {
        val text = "code: ${action.code}, error: ${action.error}"
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    private fun goToSplash() {
        Intent(requireContext(), SplashActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .let { intent -> startActivity(intent) }
    }

    private fun logout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                goToSplash()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }
}