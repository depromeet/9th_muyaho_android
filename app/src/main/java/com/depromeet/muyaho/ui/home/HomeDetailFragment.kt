package com.depromeet.muyaho.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentHomeDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailFragment :
    BaseFragment<FragmentHomeDetailBinding, HomeDetailViewModel, HomeDetailViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.fragment_home_detail
    override val vm: HomeDetailViewModel by viewModels()
    private val args: HomeDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeDetailContentAdapter(this, args.memberStockStatus)
        binding.vpContent.adapter = adapter

        TabLayoutMediator(binding.tlTitle, binding.vpContent) { tab, position ->
            tab.text = when (position) {
                0 -> "국내주식"
                1 -> "해외주식"
                2 -> "가상화폐"
                else -> ""
            }
        }.attach()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivRefresh.setOnClickListener {
            val pageIndex = binding.vpContent.currentItem
            val currentFragment = adapter.fragmentMap[pageIndex]
            if (currentFragment is HomeDetailContentFragment) {
                currentFragment.vm.getMemberStockStatus()
            }
        }
    }
}