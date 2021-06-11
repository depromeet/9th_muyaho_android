package com.depromeet.muyaho.ui.calc

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentIncomeRateBinding
import com.depromeet.muyaho.ui.modifystock.ModifyStockSelectFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncomeRateFragment :
    BaseFragment<FragmentIncomeRateBinding, IncomeRateViewModel, IncomeRateViewModel.ViewAction>() {

    override val layoutResId: Int = R.layout.fragment_income_rate
    override val vm: IncomeRateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.predictAmount.observe(viewLifecycleOwner, { amount ->
            val predictAmount = amount.toDouble()
            val resId = when {
                predictAmount < 0 -> R.drawable.img_youngchan_default_130
                predictAmount < 500000 -> R.drawable.img_youngchan_cycle_130
                predictAmount < 3000000 -> R.drawable.img_youngchan_hover_130
                predictAmount < 10000000 -> R.drawable.img_youngchan_scooter_130
                predictAmount < 20000000 -> R.drawable.img_youngchan_harley_130
                predictAmount < 50000000 -> R.drawable.img_youngchan_car1_130
                predictAmount < 100000000 -> R.drawable.img_youngchan_car2_130
                predictAmount < 300000000 -> R.drawable.img_youngchan_car3_130
                predictAmount < 500000000 -> R.drawable.img_youngchan_airplane_130
                else -> R.drawable.img_youngchan_rocket_130
            }
            binding.ivPredict.setImageResource(resId)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = IncomeRateFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvShare.setOnClickListener {
            val holdingPrice = binding.etHoldingPrice.text.toString().toBigDecimal()
            val holdingCount = binding.etHoldingCount.text.toString().toBigDecimal()
            val budget = holdingPrice * holdingCount
            val benefitRate = binding.etTargetIncomeRate.text.toString().toBigDecimal()
            CalcFragmentDirections.actionCalcToShare(budget.toLong(), benefitRate.toFloat()).also {
                findNavController().navigate(it)
            }
        }
    }
}