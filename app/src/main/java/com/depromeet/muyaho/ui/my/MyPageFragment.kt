package com.depromeet.muyaho.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentMypageBinding
import com.depromeet.muyaho.ui.my.MyPageViewModel.ViewAction.ShowError
import com.depromeet.muyaho.ui.splash.SplashActivity

class MyFragment :
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_mypage, container, false)
}