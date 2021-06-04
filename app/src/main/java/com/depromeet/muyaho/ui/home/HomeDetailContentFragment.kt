package com.depromeet.muyaho.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.data.MemberStockStatus
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.databinding.FragmentHomeDetailContentBinding
import com.depromeet.muyaho.util.NumberFormatUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailContentFragment :
    BaseFragment<FragmentHomeDetailContentBinding, HomeDetailContentViewModel, HomeDetailContentViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.fragment_home_detail_content
    override val vm: HomeDetailContentViewModel by viewModels()

    companion object {
        const val ARG_MEMBER_STOCK_LIST_KEY = "arg_member_stock_list_key"
        const val ARG_STOCK_TYPE = "arg_stock_type"
    }

    override fun onStart() {
        super.onStart()
        vm.getMemberStockStatusCache()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = arguments?.getParcelableArray(ARG_MEMBER_STOCK_LIST_KEY)?.toList() as List<MemberStock>
        val stockType = arguments?.getString(ARG_STOCK_TYPE)
        vm.stockType = stockType
        when (stockType) {
            StockType.Domestic.full_name -> {
                binding.ivYoungchan.setImageResource(R.drawable.image_youngchan_korean)
            }
            StockType.Overseas.full_name -> {
                binding.ivYoungchan.setImageResource(R.drawable.image_youngchan_american)
            }
            StockType.Bitcoin.full_name -> {
                binding.ivYoungchan.setImageResource(R.drawable.image_youngchan_coin)
            }
        }

        val adapter = HomeDetailStockAdapter()
        adapter.editButtonClickListener = object : HomeDetailStockAdapter.OnEditButtonClickListener{
            override fun onModifyButtonClicked(item: MemberStock) {
                HomeDetailFragmentDirections.actionDetailToModify(item).also {
                    findNavController().navigate(it)
                }
            }

            override fun onDeleteButtonClicked(item: MemberStock) {
                vm.deleteMemberStock(item)
            }
        }
        binding.rvStock.adapter = adapter
        adapter.submitList(list)

        binding.ivSetting.setOnClickListener {
            adapter.isEditMode = !adapter.isEditMode
            adapter.notifyDataSetChanged()
        }

        // observe
        vm.memberStockStatus.observe(viewLifecycleOwner) {
            val stockType = arguments?.getString(ARG_STOCK_TYPE)
            val list = when (stockType) {
                StockType.Domestic.full_name -> {
                    it.overview.domesticStocks
                }
                StockType.Overseas.full_name -> {
                    it.overview.foreignStocks
                }
                StockType.Bitcoin.full_name -> {
                    it.overview.bitCoins
                }
                else -> {
                    null
                }
            }

            list?.let {
                observeMemberStockStatus(adapter, stockType!!, it)
            }
        }
    }

    fun observeMemberStockStatus(adapter: HomeDetailStockAdapter, stockType: String, list: List<MemberStock>) {
        adapter.submitList(list)

        var budget = 0.0f
        var benefit = 0.0f
        list.forEach {
            if (stockType == StockType.Overseas.full_name) {
                budget += it.current.won.amountPrice.toFloat()
                benefit += it.current.won.amountPrice.toFloat() - it.purchase.amountInWon.toFloat()
            } else {
                budget += it.current.won.amountPrice.toFloat()
                benefit += it.current.won.amountPrice.toFloat() - it.purchase.amount.toFloat()
            }
        }

        binding.tvBudget.text = NumberFormatUtil.numWithComma(budget)
        binding.chBenefit.text = "${NumberFormatUtil.numWithComma(benefit)}원"
        if (benefit >= 0) {
            binding.chBenefit.setChipIconResource(R.drawable.ic_upward_red_16)
        } else {
            binding.chBenefit.setChipIconResource(R.drawable.ic_downward_blue_16)
        }
        binding.tvStockCount.text = "총 ${list.size}종목"
    }
}