package com.depromeet.muyaho.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.depromeet.muyaho.R
import com.depromeet.muyaho.data.MemberStockStatus
import com.depromeet.muyaho.databinding.LayoutBudgetBinding
import com.depromeet.muyaho.util.NumberFormatUtil

class BudgetLayout@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutBudgetBinding by lazy {
        LayoutBudgetBinding.inflate(LayoutInflater.from(context))
    }

    var onInputInvestmentButtonClickListener: OnInputInvestmentButtonClickListener? = null
    interface OnInputInvestmentButtonClickListener {
        fun onInputInvestmentButtonClicked()
    }

    init {
        addView(binding.root)

        binding.clInputInvestment.setOnClickListener {
            onInputInvestmentButtonClickListener?.onInputInvestmentButtonClicked()
        }
    }

    fun setData(memberStockStatus: MemberStockStatus) {
        if (memberStockStatus.seedAmount.isBlank() ||
                memberStockStatus.finalAsset.isBlank() ||
                memberStockStatus.todayProfitOrLose.isBlank()) {
            binding.clInputInvestment.visibility = View.VISIBLE
            binding.clBudget.visibility = View.GONE
        } else {
            binding.clInputInvestment.visibility = View.GONE
            binding.clBudget.visibility = View.VISIBLE
        }

        binding.tvSeed.text = "시드 ${NumberFormatUtil.numWithComma(memberStockStatus.seedAmount.toFloat())}"
        binding.tvBudget.text = NumberFormatUtil.numWithComma(memberStockStatus.finalAsset.toFloat())
        binding.tvBenefitPercent.text = "${memberStockStatus.finalProfitOrLoseRate}%"
        if (memberStockStatus.finalProfitOrLoseRate.toInt() > 0) {
            binding.tvBenefitPercent.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upward_red_16, 0, 0, 0)
        } else {
            binding.tvBenefitPercent.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_downward_blue_16, 0, 0, 0)
        }

        val benefitPrice = memberStockStatus.finalAsset.toFloat() - memberStockStatus.seedAmount.toFloat()
        binding.tvBenefitPrice.text = NumberFormatUtil.numWithComma(benefitPrice)
        if (benefitPrice > 0) {
            binding.tvBenefitPrice.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upward_red_16, 0, 0, 0)
        } else {
            binding.tvBenefitPrice.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_downward_blue_16, 0, 0, 0)
        }
    }
}