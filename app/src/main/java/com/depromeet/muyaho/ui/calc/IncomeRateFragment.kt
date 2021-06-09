package com.depromeet.muyaho.ui.calc

import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentIncomeRateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncomeRateFragment :
    BaseFragment<FragmentIncomeRateBinding, IncomeRateViewModel, IncomeRateViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_income_rate
    override val vm: IncomeRateViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = IncomeRateFragment()
    }
}