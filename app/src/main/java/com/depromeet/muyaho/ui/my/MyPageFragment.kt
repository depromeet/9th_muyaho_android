package com.depromeet.muyaho.ui.my

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentMypageBinding
import com.depromeet.muyaho.ui.my.MyPageViewModel.ViewAction.ShowError
import com.depromeet.muyaho.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMypageBinding, MyPageViewModel, MyPageViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_mypage
    override val vm: MyPageViewModel by viewModels()

    override fun observeActionCommand(action: MyPageViewModel.ViewAction) {
        when (action) {
            MyPageViewModel.ViewAction.GoSplash -> goToSplash()
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

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }
}