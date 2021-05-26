package com.depromeet.muyaho.ui.modifystock

import androidx.activity.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivityModifyStockBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyStockActivity : BaseActivity<ActivityModifyStockBinding, ModifyStockViewModel, ModifyStockViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.activity_modify_stock
    override val vm: ModifyStockViewModel by viewModels()
}