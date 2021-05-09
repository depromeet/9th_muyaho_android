package com.depromeet.muyaho.addstock

import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentAddStockSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStockSearchFragment :
    BaseFragment<FragmentAddStockSearchBinding,
        AddStockSearchViewModel,
        AddStockSearchViewModel.ViewAction>(){
    override val layoutResId: Int
        get() = R.layout.fragment_add_stock_search
    override val vm: AddStockSearchViewModel by viewModels()
}