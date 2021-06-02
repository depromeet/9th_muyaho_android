package com.depromeet.muyaho.my

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivityNicknameBinding
import com.depromeet.muyaho.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameActivity :
    BaseActivity<ActivityNicknameBinding, NicknameViewModel, NicknameViewModel.ViewAction>() {
    override val layoutResId: Int = R.layout.activity_nickname
    override val vm: NicknameViewModel by viewModels()

    override fun observeActionCommand(action: NicknameViewModel.ViewAction) {
        when (action) {
            NicknameViewModel.ViewAction.GoMain -> goToMain()
            is NicknameViewModel.ViewAction.ShowError -> showError(action)
        }
    }

    private fun goToMain() {
        Intent(this, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .let { intent -> startActivity(intent) }
    }

    private fun showError(action: NicknameViewModel.ViewAction.ShowError) {
        val text = "code: ${action.code}, error: ${action.error}"
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}