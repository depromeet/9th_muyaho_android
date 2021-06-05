package com.depromeet.muyaho.ui.modifystock

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.databinding.FragmentModifyStockSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyStockSelectFragment :
    BaseFragment<FragmentModifyStockSelectBinding,
            ModifyStockSelectViewModel,
            ModifyStockSelectViewModel.ViewAction>(){
    override val layoutResId: Int
        get() = R.layout.fragment_modify_stock_select
    override val vm: ModifyStockSelectViewModel by viewModels()

    private var selectedItem: MemberStock? = null

    override fun observeActionCommand(action: ModifyStockSelectViewModel.ViewAction) {
        when (action) {
            ModifyStockSelectViewModel.ViewAction.CloseActivity -> {
                requireActivity().finish()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init
        val adapter = ModifyStockListAdapter()
        adapter.mOnItemSelectListener = object : ModifyStockListAdapter.OnItemSelectListener{
            override fun OnItemSelected(item: MemberStock) {
                selectedItem = item
                binding.tvNext.isEnabled = true
            }
        }
        binding.rvStock.adapter = adapter

        binding.tvNext.setOnClickListener {
            selectedItem?.let { memberStock ->
                ModifyStockSelectFragmentDirections.actionSelectToInput(memberStock).also {
                    findNavController().navigate(it)
                }
            }
        }

        binding.rgStockType.setOnCheckedChangeListener(vm.stockTypeCheckChangeListener)

        // observe
        vm.stockList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            selectedItem = null
            adapter.selectedIndex = -1
            binding.tvNext.isEnabled = false
        }

        // first exec
        binding.rgStockType.check(binding.rbDomesticStock.id)
    }
}