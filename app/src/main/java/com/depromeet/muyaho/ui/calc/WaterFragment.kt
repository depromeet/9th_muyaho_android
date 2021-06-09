package com.depromeet.muyaho.ui.calc

import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentWaterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaterFragment :
    BaseFragment<FragmentWaterBinding, WaterViewModel, WaterViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_water
    override val vm: WaterViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = WaterFragment()
    }
}