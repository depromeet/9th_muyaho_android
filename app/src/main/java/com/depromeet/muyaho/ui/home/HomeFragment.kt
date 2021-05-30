package com.depromeet.muyaho.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentHomeBinding
import com.depromeet.muyaho.ui.addstock.AddStockActivity
import com.depromeet.muyaho.util.NumberFormatUtil
import com.depromeet.muyaho.widget.BudgetLayout
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init
        binding.budget.onInputInvestmentButtonClickListener = object : BudgetLayout.OnInputInvestmentButtonClickListener{
            override fun onInputInvestmentButtonClicked() {
                Intent(requireContext(), AddStockActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        // observe
        vm.memberStockStatus.observe(viewLifecycleOwner) {
            if (it.todayProfitOrLose.isBlank()) {
                // 입력이 되지 않은 상태
                binding.tvTodayBenefitEmpty.visibility = View.VISIBLE
                binding.llTodayBenefit.visibility = View.GONE
                binding.ivYoungchan.setImageResource(R.drawable.image_youngchan_gray)
            } else {
                binding.tvTodayBenefitEmpty.visibility = View.GONE
                binding.llTodayBenefit.visibility = View.VISIBLE
                binding.tvTodayBenefit.text = it.todayProfitOrLose

                val nTodayProfitOrLose = it.todayProfitOrLose.toInt()
                if (nTodayProfitOrLose > 0) {
                    // 수익이 난 상태
                    binding.tvTodayBenefit.setTextColor(resources.getColor(R.color.secondary_red, null))
                    binding.ivYoungchan.setImageResource(R.drawable.image_youngchan_red)
                } else {
                    // 손실이 난 상태
                    binding.tvTodayBenefit.setTextColor(resources.getColor(R.color.secondary_blue, null))
                    binding.ivYoungchan.setImageResource(R.drawable.image_youngchan_blue)
                }
            }

            binding.budget.setData(it)

            var sum = 0
            if (it.overview.domesticStocks.isNotEmpty()) {
                it.overview.domesticStocks.forEach {
                    val benefit = it.current.won.amountPrice.toInt() - it.purchase.amount.toInt()
                    sum += benefit
                }

                if (sum > 0) {
                    binding.tvInvestDomesticBenefit.text = "+${NumberFormatUtil.numWithComma(sum.toFloat())}"
                    binding.tvInvestDomesticBenefit.setTextColor(resources.getColor(R.color.secondary_red, null))
                } else {
                    binding.tvInvestDomesticBenefit.text = NumberFormatUtil.numWithComma(sum.toFloat())
                    binding.tvInvestDomesticBenefit.setTextColor(resources.getColor(R.color.secondary_blue, null))
                }
            } else {
                binding.tvInvestDomesticBenefit.text = "등록하기"
                binding.tvInvestDomesticBenefit.setTextColor(resources.getColor(R.color.black_5, null))
            }

            if (it.overview.foreignStocks.isNotEmpty()) {
                it.overview.foreignStocks.forEach {
                    val benefit = it.current.won.amountPrice.toInt() - it.purchase.amountInWon.toInt()
                    sum += benefit
                }

                if (sum > 0) {
                    binding.tvInvestOverseasBenefit.text = "+${NumberFormatUtil.numWithComma(sum.toFloat())}"
                    binding.tvInvestOverseasBenefit.setTextColor(resources.getColor(R.color.secondary_red, null))
                } else {
                    binding.tvInvestOverseasBenefit.text = NumberFormatUtil.numWithComma(sum.toFloat())
                    binding.tvInvestOverseasBenefit.setTextColor(resources.getColor(R.color.secondary_blue, null))
                }
            } else {
                binding.tvInvestOverseasBenefit.text = "등록하기"
                binding.tvInvestOverseasBenefit.setTextColor(resources.getColor(R.color.black_5, null))
            }

            if (it.overview.bitCoins.isNotEmpty()) {
                it.overview.bitCoins.forEach {
                    val benefit = it.current.won.amountPrice.toInt() - it.purchase.amount.toInt()
                    sum += benefit
                }

                if (sum > 0) {
                    binding.tvInvestBitcoinBenefit.text = "+${NumberFormatUtil.numWithComma(sum.toFloat())}"
                    binding.tvInvestBitcoinBenefit.setTextColor(resources.getColor(R.color.secondary_red, null))
                } else {
                    binding.tvInvestBitcoinBenefit.text = NumberFormatUtil.numWithComma(sum.toFloat())
                    binding.tvInvestBitcoinBenefit.setTextColor(resources.getColor(R.color.secondary_blue, null))
                }
            } else {
                binding.tvInvestBitcoinBenefit.text = "등록하기"
                binding.tvInvestBitcoinBenefit.setTextColor(resources.getColor(R.color.black_5, null))
            }
        }
    }
}