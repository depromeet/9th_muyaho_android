package com.depromeet.muyaho.ui.home

import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentHomeBinding
import com.depromeet.muyaho.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel, HomeViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.fragment_home
    override val vm: HomeViewModel by viewModels()

    override fun observeActionCommand(action: HomeViewModel.ViewAction) {
        when (action) {

        }
    }
}