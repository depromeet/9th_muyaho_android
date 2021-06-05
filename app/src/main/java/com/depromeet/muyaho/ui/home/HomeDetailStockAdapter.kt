package com.depromeet.muyaho.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.muyaho.R
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.databinding.ListItemHomeDetailStockBinding
import com.depromeet.muyaho.util.NumberFormatUtil
import com.depromeet.muyaho.widget.SmallAlertDialog

class HomeDetailStockAdapter: ListAdapter<MemberStock, RecyclerView.ViewHolder>(MemberStockDiffCallback()) {

    var isEditMode = false

    var editButtonClickListener: OnEditButtonClickListener? = null
    interface OnEditButtonClickListener {
        fun onModifyButtonClicked(item: MemberStock)
        fun onDeleteButtonClicked(item: MemberStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MemberStockViewHolder(
            ListItemHomeDetailStockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val memberStock = getItem(position)
        (holder as MemberStockViewHolder).bind(memberStock)
    }

    inner class MemberStockViewHolder(
        private val binding: ListItemHomeDetailStockBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MemberStock) {

            binding.tvStockName.text = item.stock.name

            val benefit = if (item.stock.type == StockType.Overseas.full_name) {
                item.current.won.amountPrice.toBigDecimal().minus(item.purchase.amountInWon?.toBigDecimal() ?: 0.toBigDecimal())
            } else {
                item.current.won.amountPrice.toBigDecimal() - item.purchase.amount.toBigDecimal()
            }
            if (benefit > 0.toBigDecimal()) {
                binding.tvStockBenefit.setTextColor(binding.root.resources.getColor(R.color.secondary_red, null))
                binding.tvStockBenefit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upward_red_16, 0, 0, 0)
            } else {
                binding.tvStockBenefit.setTextColor(binding.root.resources.getColor(R.color.secondary_blue, null))
                binding.tvStockBenefit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_downward_blue_16, 0, 0, 0)
            }
            binding.tvStockBenefit.text = "${NumberFormatUtil.numWithComma(benefit)}(${item.profitOrLoseRate}%)"

            binding.tvStockPrice.text = NumberFormatUtil.numWithComma(item.current.won.amountPrice.toBigDecimal())
            when (item.stock.type) {
                StockType.Domestic.full_name -> {
                    binding.clDetailDefault.visibility = View.VISIBLE
                    binding.clDetailBitcoin.visibility = View.GONE

                    binding.tvNowPrice.text = NumberFormatUtil.numWithComma(item.current.won.unitPrice.toBigDecimal())
                    binding.tvAveragePrice.text = NumberFormatUtil.numWithComma(item.purchase.unitPrice.toBigDecimal())
                    binding.tvStockCount.text = NumberFormatUtil.numWithComma(item.quantity.toBigDecimal())
                }
                StockType.Overseas.full_name -> {
                    binding.clDetailDefault.visibility = View.VISIBLE
                    binding.clDetailBitcoin.visibility = View.GONE

                    binding.tvNowPrice.text = NumberFormatUtil.numWithComma(item.current.dollar.unitPrice.toBigDecimal())
                    binding.tvAveragePrice.text = NumberFormatUtil.numWithComma(item.purchase.unitPrice.toBigDecimal())
                    binding.tvStockCount.text = NumberFormatUtil.numWithComma(item.quantity.toBigDecimal())
                }
                StockType.Bitcoin.full_name -> {
                    binding.clDetailDefault.visibility = View.GONE
                    binding.clDetailBitcoin.visibility = View.VISIBLE

                    binding.tvBitcoinNowPrice.text = NumberFormatUtil.numWithComma(item.current.won.unitPrice.toBigDecimal())
                    binding.tvBitcoinAveragePrice.text = NumberFormatUtil.numWithComma(item.purchase.unitPrice.toBigDecimal())
                }
            }

            if (isEditMode) {
                binding.clDetailEdit.visibility = View.VISIBLE
            } else {
                binding.clDetailEdit.visibility = View.GONE
            }

            binding.flEditModify.setOnClickListener {
                editButtonClickListener?.onModifyButtonClicked(item)
            }

            binding.flEditDelete.setOnClickListener {
                SmallAlertDialog(binding.root.context, "종목을 삭제할까요?", "최종 자산과 수익률에 영향을 줍니다.",
                "삭제하기", {
                        editButtonClickListener?.onDeleteButtonClicked(item)
                    },
                "다음에", {
                    }).show()
            }
        }


    }
}

private class MemberStockDiffCallback: DiffUtil.ItemCallback<MemberStock>() {
    override fun areItemsTheSame(oldItem: MemberStock, newItem: MemberStock): Boolean {
        return oldItem.memberStockId == newItem.memberStockId
    }

    override fun areContentsTheSame(oldItem: MemberStock, newItem: MemberStock): Boolean {
        return oldItem == newItem
    }
}