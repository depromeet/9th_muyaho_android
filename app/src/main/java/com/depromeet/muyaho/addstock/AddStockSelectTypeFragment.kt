package com.depromeet.muyaho.addstock

import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentAddStockSelectTypeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStockSelectTypeFragment :
    BaseFragment<FragmentAddStockSelectTypeBinding,
            AddStockSelectTypeViewModel,
            AddStockSelectTypeViewModel.ViewAction>(){
    override val layoutResId: Int
        get() = R.layout.fragment_add_stock_select_type
    override val vm: AddStockSelectTypeViewModel by viewModels()
}