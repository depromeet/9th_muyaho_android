package com.depromeet.muyaho.ui.calc

import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment :
    BaseFragment<FragmentHistoryBinding, HistoryViewModel, HistoryViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_history
    override val vm: HistoryViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}