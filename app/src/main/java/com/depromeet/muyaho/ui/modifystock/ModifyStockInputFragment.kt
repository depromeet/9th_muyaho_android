package com.depromeet.muyaho.ui.modifystock

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.databinding.FragmentModifyStockInputBinding
import com.depromeet.muyaho.util.NumberFormatUtil
import com.depromeet.muyaho.widget.PriceEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyStockInputFragment :
    BaseFragment<FragmentModifyStockInputBinding,
        ModifyStockInputViewModel,
        ModifyStockInputViewModel.ViewAction>(){
    override val layoutResId: Int
        get() = R.layout.fragment_modify_stock_input
    override val vm: ModifyStockInputViewModel by viewModels()
    private val args: ModifyStockInputFragmentArgs by navArgs()

    private val imm: InputMethodManager by lazy {
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init
        binding.lsWonDollar.setOnToggledListener { toggleableView, isOn ->
            vm.setDollarState(isOn)
        }

        binding.petAveragePrice.mOnEditCompleteListener = object : PriceEditText.OnEditCompleteListener{
            override fun OnComplete() {
                updatePrice()
            }
        }

        binding.petPurchasePrice.mOnEditCompleteListener = object : PriceEditText.OnEditCompleteListener{
            override fun OnComplete() {
                updatePrice()
            }
        }

        binding.petQuantity.mOnEditCompleteListener = object : PriceEditText.OnEditCompleteListener{
            override fun OnComplete() {
                updatePrice()
            }
        }

        binding.tvSave.setOnClickListener {
            vm.putMemberStock(args.memberStock.memberStockId, binding.petAveragePrice.price.toInt(), binding.petQuantity.price.toInt())
        }

        // observe
        vm.isDollar.observe(viewLifecycleOwner) {
            if (it) {
                binding.petAveragePrice.price = ""
                binding.petAveragePrice.priceType = PriceEditText.PriceType.DOLLAR
            } else {
                binding.petAveragePrice.price = ""
                binding.petAveragePrice.priceType = PriceEditText.PriceType.WON
            }
            binding.petAveragePrice.updateFormatPrice()
            updatePrice()
        }

        vm.isPutComplete.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().finish()
            }
        }

        // first exec
        when (args.memberStock.stock.type) {
            StockType.Domestic.full_name -> {
                binding.tvStockType.text = "국내주식"

                binding.llInputPurchaseAmount.visibility = View.GONE
                binding.petAveragePrice.priceType = PriceEditText.PriceType.WON
                binding.petQuantity.priceType = PriceEditText.PriceType.COUNT
            }
            StockType.Overseas.full_name -> {
                binding.tvStockType.text = "해외주식"

                binding.llInputPurchaseAmount.visibility = View.GONE
                binding.petAveragePrice.priceType = PriceEditText.PriceType.WON
                binding.petQuantity.priceType = PriceEditText.PriceType.COUNT
            }
            StockType.Bitcoin.full_name -> {
                binding.tvStockType.text = "가상화폐"

                binding.llInputPurchaseAmount.visibility = View.VISIBLE
                binding.petAveragePrice.priceType = PriceEditText.PriceType.WON
                binding.petPurchasePrice.priceType = PriceEditText.PriceType.WON
                binding.petQuantity.priceType = PriceEditText.PriceType.COUNT
            }
        }

        binding.tvStockName.text = args.memberStock.stock.name
        binding.tvStockPrice.text = NumberFormatUtil.numWithComma(args.memberStock.purchaseAmount.toFloat())
        binding.petAveragePrice.price = args.memberStock.purchasePrice
        binding.petPurchasePrice.price = args.memberStock.purchaseAmount
        binding.petQuantity.price = args.memberStock.quantity
    }

    fun updatePrice() {
        val averagePrice = binding.petAveragePrice.price.let {
            if (it.isBlank()) {
                0.0f
            } else {
                it.toFloat()
            }
        }

        val stockCount = binding.petQuantity.price.let {
            if (it.isBlank()) {
                0
            } else {
                it.toInt()
            }
        }

        val price: Float = averagePrice * stockCount

        val isDollar = vm.isDollar.value?: false
        if (isDollar) {
            binding.tvStockPrice.text = NumberFormatUtil.numWithDollar(price)
        } else {
            binding.tvStockPrice.text = NumberFormatUtil.numWithComma(price)
        }
    }
}