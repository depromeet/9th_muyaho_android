package com.depromeet.muyaho.ui.addstock

import androidx.activity.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivityAddStockBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStockActivity : BaseActivity<ActivityAddStockBinding, AddStockViewModel, AddStockViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.activity_add_stock
    override val vm: AddStockViewModel by viewModels()
}