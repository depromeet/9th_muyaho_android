package com.depromeet.muyaho.ui.addstock

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.data.Stock
import com.depromeet.muyaho.databinding.FragmentAddStockSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStockSearchFragment :
    BaseFragment<FragmentAddStockSearchBinding,
        AddStockSearchViewModel,
        AddStockSearchViewModel.ViewAction>(){
    override val layoutResId: Int
        get() = R.layout.fragment_add_stock_search
    override val vm: AddStockSearchViewModel by viewModels()
    private val args: AddStockSearchFragmentArgs by navArgs()

    private val imm: InputMethodManager by lazy {
        context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init
        val adapter = AddStockSearchListAdapter().apply {
            mOnItemClickListener = object : AddStockSearchListAdapter.OnItemClickListener{
                override fun OnClick(item: Stock) {
                    AddStockSearchFragmentDirections.actionSearchToInput(item).also {
                        findNavController().navigate(it)
                    }
                }
            }
        }
        binding.rvSearchResult.adapter = adapter
        vm.stocks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.etSearch.addTextChangedListener {
            vm.setSearchWord(it.toString())
            if (it?.length == 0) {
                binding.ivDelete.visibility = View.GONE
            } else {
                binding.ivDelete.visibility = View.VISIBLE
            }
        }
        binding.etSearch.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER) {
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                true
            } else {
                false
            }
        }

        binding.ivDelete.setOnClickListener {
            binding.etSearch.setText("")
        }

        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }

        // first exec
        vm.stockType = args.stockType

        binding.etSearch.requestFocus()
        imm.showSoftInput(binding.etSearch, 0)

        vm.setSearchWord("")
    }
}