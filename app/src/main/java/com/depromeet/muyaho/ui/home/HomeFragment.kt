package com.depromeet.muyaho.ui.home

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.data.MemberStockStatus
import com.depromeet.muyaho.databinding.FragmentHomeBinding
import com.depromeet.muyaho.other.Constants
import com.depromeet.muyaho.ui.addstock.AddStockActivity
import com.depromeet.muyaho.util.NumberFormatUtil
import com.depromeet.muyaho.widget.BudgetLayout
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel, HomeViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.fragment_home
    override val vm: HomeViewModel by viewModels()

    override fun observeActionCommand(action: HomeViewModel.ViewAction) {
        when (action) {

        }
    }

    override fun onStart() {
        super.onStart()
        vm.getMemberStockStatusCache()
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

        binding.clInvestDomestic.setOnClickListener {
            if (vm.memberStockStatus.value?.overview?.domesticStocks?.size?:0 > 0) {
                navigateToDetail(0)
            } else {
                Intent(requireContext(), AddStockActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        binding.clInvestOverseas.setOnClickListener {
            if (vm.memberStockStatus.value?.overview?.foreignStocks?.size?:0 > 0) {
                navigateToDetail(1)
            } else {
                Intent(requireContext(), AddStockActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        binding.clInvestBitcoin.setOnClickListener {
            if (vm.memberStockStatus.value?.overview?.bitCoins?.size?:0 > 0) {
                navigateToDetail(2)
            } else {
                Intent(requireContext(), AddStockActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        binding.ivRefresh.setOnClickListener {
            vm.getMemberStockStatus()
        }

        // observe
        vm.memberStockStatus.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }

            if (it.seedAmount.isBlank() || it.seedAmount == "0") {
                // 입력이 되지 않은 상태
                binding.tvTodayBenefitEmpty.visibility = View.VISIBLE
                binding.llTodayBenefit.visibility = View.GONE
                binding.ivYoungchan.setImageResource(R.drawable.image_youngchan_gray)
            } else {
                binding.tvTodayBenefitEmpty.visibility = View.GONE
                binding.llTodayBenefit.visibility = View.VISIBLE
                if (it.todayProfitOrLose.toBigDecimal() >= 0.toBigDecimal()) {
                    binding.tvTodayBenefit.text = "+${NumberFormatUtil.numWithComma(it.todayProfitOrLose.toBigDecimal())}"
                } else {
                    binding.tvTodayBenefit.text = NumberFormatUtil.numWithComma(it.todayProfitOrLose.toBigDecimal())
                }

                val nTodayProfitOrLose = it.todayProfitOrLose.toBigDecimal()
                if (nTodayProfitOrLose > 0.toBigDecimal()) {
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
            setYoungchanMent(it)

            if (it.overview.domesticStocks.isNotEmpty()) {
                var sum = BigDecimal(0)
                it.overview.domesticStocks.forEach {
                    val benefit = it.current.won.amountPrice.toBigDecimal() - it.purchase.amount.toBigDecimal()
                    sum += benefit
                }

                if (sum > 0.toBigDecimal()) {
                    binding.tvInvestDomesticBenefit.text = "+${NumberFormatUtil.numWithComma(sum)}"
                    binding.tvInvestDomesticBenefit.setTextColor(resources.getColor(R.color.secondary_red, null))
                } else {
                    binding.tvInvestDomesticBenefit.text = NumberFormatUtil.numWithComma(sum)
                    binding.tvInvestDomesticBenefit.setTextColor(resources.getColor(R.color.secondary_blue, null))
                }
            } else {
                binding.tvInvestDomesticBenefit.text = "등록하기"
                binding.tvInvestDomesticBenefit.setTextColor(resources.getColor(R.color.black_5, null))
            }

            if (it.overview.foreignStocks.isNotEmpty()) {
                var sum = BigDecimal(0)
                it.overview.foreignStocks.forEach {
                    val benefit = it.current.won.amountPrice.toBigDecimal().minus(it.purchase.amountInWon?.toBigDecimal() ?: 0.toBigDecimal())
                    sum += benefit
                }

                if (sum > 0.toBigDecimal()) {
                    binding.tvInvestOverseasBenefit.text = "+${NumberFormatUtil.numWithComma(sum)}"
                    binding.tvInvestOverseasBenefit.setTextColor(resources.getColor(R.color.secondary_red, null))
                } else {
                    binding.tvInvestOverseasBenefit.text = NumberFormatUtil.numWithComma(sum)
                    binding.tvInvestOverseasBenefit.setTextColor(resources.getColor(R.color.secondary_blue, null))
                }
            } else {
                binding.tvInvestOverseasBenefit.text = "등록하기"
                binding.tvInvestOverseasBenefit.setTextColor(resources.getColor(R.color.black_5, null))
            }

            if (it.overview.bitCoins.isNotEmpty()) {
                var sum = BigDecimal(0)
                it.overview.bitCoins.forEach {
                    val benefit = it.current.won.amountPrice.toBigDecimal() - it.purchase.amount.toBigDecimal()
                    sum += benefit
                }

                if (sum > 0.toBigDecimal()) {
                    binding.tvInvestBitcoinBenefit.text = "+${NumberFormatUtil.numWithComma(sum)}"
                    binding.tvInvestBitcoinBenefit.setTextColor(resources.getColor(R.color.secondary_red, null))
                } else {
                    binding.tvInvestBitcoinBenefit.text = NumberFormatUtil.numWithComma(sum)
                    binding.tvInvestBitcoinBenefit.setTextColor(resources.getColor(R.color.secondary_blue, null))
                }
            } else {
                binding.tvInvestBitcoinBenefit.text = "등록하기"
                binding.tvInvestBitcoinBenefit.setTextColor(resources.getColor(R.color.black_5, null))
            }
        }
    }

    fun setYoungchanMent(memberStockStatus: MemberStockStatus) {

        val colorId: Int
        val ment01: String
        val ment02: String

        val random = Random()
        if (memberStockStatus.seedAmount.isBlank() || memberStockStatus.seedAmount == "0") {
            // 입력이 되지 않은 상태
            ment01 = Constants.YOUNGCHAN_MENT_NONE[0][0]
            ment02 = Constants.YOUNGCHAN_MENT_NONE[0][1]

            colorId = Color.parseColor("#D0D0D0")
        } else {
            val nTodayProfitOrLose = memberStockStatus.todayProfitOrLose.toBigDecimal()
            if (nTodayProfitOrLose > 0.toBigDecimal()) {
                // 수익이 난 상태
                val index = random.nextInt(3)

                ment01 = Constants.YOUNGCHAN_MENT_PLUS[index][0]
                ment02 = Constants.YOUNGCHAN_MENT_PLUS[index][1]

                colorId = resources.getColor(R.color.secondary_red, null)
            } else {
                // 손실이 난 상태
                val index = random.nextInt(3)

                ment01 = Constants.YOUNGCHAN_MENT_MINUS[index][0]
                ment02 = Constants.YOUNGCHAN_MENT_MINUS[index][1]

                colorId = resources.getColor(R.color.primary_blue_dark, null)
            }
        }

        binding.chYoungchanMent01.text = ment01
        binding.chYoungchanMent02.text = ment02
        binding.chYoungchanMent01.chipBackgroundColor = ColorStateList.valueOf(colorId)
        binding.chYoungchanMent02.chipBackgroundColor = ColorStateList.valueOf(colorId)
    }

    fun navigateToDetail(position: Int) {
        vm.memberStockStatus.value?.let {
            HomeFragmentDirections.actionHomeToDetail(it, position).also {
                findNavController().navigate(it)
            }
        }
    }
}