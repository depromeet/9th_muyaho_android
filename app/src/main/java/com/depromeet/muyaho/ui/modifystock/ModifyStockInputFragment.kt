package com.depromeet.muyaho.ui.modifystock

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.databinding.FragmentModifyStockInputBinding
import com.depromeet.muyaho.other.Constants
import com.depromeet.muyaho.ui.MainActivity
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init
        binding.petAveragePrice.mOnEditCompleteListener = object : PriceEditText.OnEditCompleteListener{
            override fun OnComplete() {
                updatePrice()
            }
        }

        binding.petPurchasePrice.mOnEditCompleteListener = object : PriceEditText.OnEditCompleteListener{
            override fun OnComplete() {
                updateStockCount()
                updatePrice()
            }
        }

        binding.petQuantity.mOnEditCompleteListener = object : PriceEditText.OnEditCompleteListener{
            override fun OnComplete() {
                updatePurchasePrice()
                updatePrice()
            }
        }

        binding.petOverseasPurchasePrice.mOnEditCompleteListener = object : PriceEditText.OnEditCompleteListener{
            override fun OnComplete() {
                binding.tvStockPrice.text = NumberFormatUtil.numWithComma(binding.petOverseasPurchasePrice.price.toBigDecimal())
            }
        }

        binding.tvSave.setOnClickListener {
            binding.petAveragePrice.clearFocus()
            binding.petPurchasePrice.clearFocus()
            binding.petQuantity.clearFocus()
            if (isInputDataValidate()) {
                vm.putMemberStock(
                    args.memberStock.memberStockId,
                    binding.petAveragePrice.price.toFloat(),
                    binding.petQuantity.price.toFloat(),
                    binding.petOverseasPurchasePrice.price.toFloat()
                )
            }
        }

        // observe
        vm.isPutComplete.observe(viewLifecycleOwner) {
            if (it) {
                if (requireActivity() !is MainActivity) {
                    requireActivity().finish()
                } else {
                    findNavController().popBackStack()
                }
            }
        }

        // first exec
        when (args.memberStock.stock.type) {
            StockType.Domestic.full_name -> {
                binding.tvStockType.text = "국내주식"

                binding.llInputPurchaseAmount.visibility = View.GONE
                binding.petAveragePrice.priceType = PriceEditText.PriceType.WON
                binding.petQuantity.priceType = PriceEditText.PriceType.COUNT

                binding.llOverseaOption.visibility = View.GONE
            }
            StockType.Overseas.full_name -> {
                binding.tvStockType.text = "해외주식"

                binding.llInputPurchaseAmount.visibility = View.GONE
                binding.petAveragePrice.priceType = PriceEditText.PriceType.DOLLAR
                binding.petQuantity.priceType = PriceEditText.PriceType.COUNT

                binding.llOverseaOption.visibility = View.VISIBLE
            }
            StockType.Bitcoin.full_name -> {
                binding.tvStockType.text = "가상화폐"

                binding.llInputPurchaseAmount.visibility = View.VISIBLE
                binding.petAveragePrice.priceType = PriceEditText.PriceType.WON
                binding.petPurchasePrice.priceType = PriceEditText.PriceType.WON
                binding.petQuantity.priceType = PriceEditText.PriceType.COUNT

                binding.llOverseaOption.visibility = View.GONE
            }
        }
        vm.stockType = args.memberStock.stock.type

        binding.tvStockName.text = args.memberStock.stock.name

        val isDollar = (args.memberStock.currencyType == "DOLLAR")
        if (isDollar) {
            binding.tvStockPrice.text = NumberFormatUtil.numWithComma(args.memberStock.purchase.amountInWon?.toBigDecimal()?: 0.toBigDecimal())
        } else {
            binding.tvStockPrice.text = NumberFormatUtil.numWithComma(args.memberStock.purchase.amount.toBigDecimal())
        }
        binding.petAveragePrice.price = args.memberStock.purchase.unitPrice
        binding.petPurchasePrice.price = args.memberStock.purchase.amount
        binding.petQuantity.price = args.memberStock.quantity
        binding.petOverseasPurchasePrice.price = args.memberStock.purchase.amountInWon?: ""
        binding.tvExchangeRate.text = NumberFormatUtil.numWithComma(Constants.OVERSEAS_EXCHANGE_RATE)
    }

    fun updatePrice() {
        val averagePrice = binding.petAveragePrice.price.let {
            if (it.isBlank()) {
                0.toBigDecimal()
            } else {
                it.toBigDecimal()
            }
        }

        val stockCount = binding.petQuantity.price.let {
            if (it.isBlank()) {
                0.toBigDecimal()
            } else {
                it.toBigDecimal()
            }
        }

        var price = averagePrice * stockCount
        val isDollar = (vm.stockType == StockType.Overseas.full_name)
        if (isDollar) {
            price *= Constants.OVERSEAS_EXCHANGE_RATE
        }

        binding.tvStockPrice.text = NumberFormatUtil.numWithComma(price)
        binding.petOverseasPurchasePrice.price = price.toString()
    }

    fun updatePurchasePrice() {
        val averagePrice = binding.petAveragePrice.price.let {
            if (it.isBlank()) {
                0
            } else {
                it.toInt()
            }
        }

        val stockCount = binding.petQuantity.price.let {
            if (it.isBlank()) {
                0.0f
            } else {
                it.toFloat()
            }
        }

        val price: Int = (averagePrice * stockCount).toInt()
        binding.petPurchasePrice.price = price.toString()
    }

    fun updateStockCount() {
        val averagePrice = binding.petAveragePrice.price.let {
            if (it.isBlank()) {
                0
            } else {
                it.toInt()
            }
        }

        val purchasePrice = binding.petPurchasePrice.price.let {
            if (it.isBlank()) {
                0.0f
            } else {
                it.toFloat()
            }
        }

        val stockCount: Float = purchasePrice / averagePrice
        binding.petQuantity.price = stockCount.toString()
    }

    fun isInputDataValidate(): Boolean {
        val averagePrice = binding.petAveragePrice.price
        if (averagePrice.isBlank() || averagePrice == "0") {
            return false
        }

        val quantity = binding.petQuantity.price
        if (quantity.isBlank() || quantity == "0") {
            return false
        }

        return true
    }
}