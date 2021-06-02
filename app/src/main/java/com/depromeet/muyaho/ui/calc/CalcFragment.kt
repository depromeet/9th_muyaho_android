package com.depromeet.muyaho.ui.calc

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentCalcBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalcFragment : BaseFragment<FragmentCalcBinding, CalcViewModel, CalcViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_calc
    override val vm: CalcViewModel by viewModels()

    override fun observeActionCommand(action: CalcViewModel.ViewAction) {
        when (action) {
            CalcViewModel.ViewAction.ShowHistory -> navigate(R.id.action_fragment_calc_to_navigation)
            CalcViewModel.ViewAction.ShowRevenueRate -> navigate(R.id.action_fragment_calc_to_navigation2)
            CalcViewModel.ViewAction.ShowWater -> navigate(R.id.action_fragment_calc_to_navigation3)
        }
    }

    private fun navigate(id: Int) {
        binding.fragmentContainer.findNavController().navigate(id)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CalcFragment()
    }
}