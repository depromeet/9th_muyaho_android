package com.depromeet.muyaho.ui

import androidx.activity.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel, MainViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.activity_main
    override val vm: MainViewModel by viewModels()

    override fun observeActionCommand(action: MainViewModel.ViewAction) {
        when (action) {
            MainViewModel.ViewAction.ChangeMainText -> showActivity()
        }
    }

    private fun showActivity() {
        binding.tvTest.text = "테스트 입니다~\n이런 흐름으로 viewmodel에서 action 통해서 view 와 통신? 상호작용? 하는 방식입니다~"
    }
}
