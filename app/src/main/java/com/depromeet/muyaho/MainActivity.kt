package com.depromeet.muyaho

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel, MainViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.activity_main
    override val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun observeActionCommand(action: MainViewModel.ViewAction) {
        Log.d("!@#", action.toString())
        when (action) {
            MainViewModel.ViewAction.ChangeMainText -> changeMainText()
        }
    }

    private fun changeMainText() {
        binding.tvTest.text = "테스트 입니다~\n이런 흐름으로 viewmodel에서 action 통해서 view 와 통신? 상호작용? 하는 방식입니다~"
    }
}