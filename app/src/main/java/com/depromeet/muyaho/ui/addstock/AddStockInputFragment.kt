package com.depromeet.muyaho.ui.addstock

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.databinding.FragmentAddStockInputBinding
import com.depromeet.muyaho.util.NumberFormatUtil
import com.depromeet.muyaho.widget.PriceEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStockInputFragment :
    BaseFragment<FragmentAddStockInputBinding,
        AddStockInputViewModel,
        AddStockInputViewModel.ViewAction>(){
    override val layoutResId: Int
        get() = R.layout.fragment_add_stock_input
    override val vm: AddStockInputViewModel by viewModels()
    private val args: AddStockInputFragmentArgs by navArgs()

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
            binding.petAveragePrice.clearFocus()
            binding.petPurchasePrice.clearFocus()
            binding.petQuantity.clearFocus()
            if (isInputDataValidate()) {
                vm.postMemberStock(
                    args.stock.id,
                    binding.petAveragePrice.price.toInt(),
                    binding.petQuantity.price.toInt()
                )
            }
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

        vm.isPostComplete.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().finish()
            }
        }

        // first exec
        when (args.stock.type) {
            StockType.Domestic.full_name -> {
                binding.tvStockType.text = "국내주식"
                binding.lsWonDollar.visibility = View.GONE

                binding.llInputPurchaseAmount.visibility = View.GONE
                binding.petAveragePrice.priceType = PriceEditText.PriceType.WON
                binding.petQuantity.priceType = PriceEditText.PriceType.COUNT
            }
            StockType.Overseas.full_name -> {
                binding.tvStockType.text = "해외주식"
                binding.lsWonDollar.visibility = View.VISIBLE

                binding.llInputPurchaseAmount.visibility = View.GONE
                binding.petAveragePrice.priceType = PriceEditText.PriceType.WON
                binding.petQuantity.priceType = PriceEditText.PriceType.COUNT
            }
            StockType.Bitcoin.full_name -> {
                binding.tvStockType.text = "가상화폐"
                binding.lsWonDollar.visibility = View.GONE

                binding.llInputPurchaseAmount.visibility = View.VISIBLE
                binding.petAveragePrice.priceType = PriceEditText.PriceType.WON
                binding.petPurchasePrice.priceType = PriceEditText.PriceType.WON
                binding.petQuantity.priceType = PriceEditText.PriceType.COUNT
            }
        }

        binding.tvStockName.text = args.stock.name
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