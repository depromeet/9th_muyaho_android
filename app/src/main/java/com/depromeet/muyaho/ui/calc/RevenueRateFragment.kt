package com.depromeet.muyaho.ui.calc

import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentRevenueRateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RevenueRateFragment :
    BaseFragment<FragmentRevenueRateBinding, RevenueRateViewModel, RevenueRateViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_revenue_rate
    override val vm: RevenueRateViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = RevenueRateFragment()
    }
}