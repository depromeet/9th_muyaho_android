package com.depromeet.muyaho.ui.calc

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentCalcBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalcFragment : BaseFragment<FragmentCalcBinding, CalcViewModel, CalcViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_calc
    override val vm: CalcViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed { vm.init() }
    }

    override fun observeActionCommand(action: CalcViewModel.ViewAction) {
        when (action) {
            CalcViewModel.ViewAction.ShowHistory -> navigate(HistoryFragment.newInstance())
            CalcViewModel.ViewAction.ShowIncomeRate -> navigate(IncomeRateFragment.newInstance())
            CalcViewModel.ViewAction.ShowWater -> navigate(WaterFragment.newInstance())
        }
    }

    private fun navigate(child: Fragment) {
        val childFt: FragmentTransaction = childFragmentManager.beginTransaction()
        if (!child.isAdded) {
            childFt.replace(R.id.fragment_container, child)
            childFt.commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CalcFragment()
    }
}