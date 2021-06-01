package com.depromeet.muyaho.my

import androidx.activity.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.base.NoAction
import com.depromeet.muyaho.databinding.ActivityNicknameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameActivity : BaseActivity<ActivityNicknameBinding, NicknameViewModel, NoAction>() {
    override val layoutResId: Int = R.layout.activity_nickname
    override val vm: NicknameViewModel by viewModels()
}