package com.depromeet.muyaho.ui.calc

import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentCalcBinding

class CalcFragment : BaseFragment<FragmentCalcBinding, CalcViewModel, CalcViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_calc
    override val vm: CalcViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = CalcFragment()
    }
}