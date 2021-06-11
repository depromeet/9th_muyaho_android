package com.depromeet.muyaho.ui.modifystock

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.muyaho.R
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.databinding.ListItemModifyStockBinding
import com.depromeet.muyaho.util.NumberFormatUtil

class ModifyStockListAdapter: ListAdapter<MemberStock, RecyclerView.ViewHolder>(MemberStockDiffCallback()) {

    var selectedIndex = -1

    var mOnItemSelectListener: OnItemSelectListener? = null
    interface OnItemSelectListener {
        fun OnItemSelected(item: MemberStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MemberStockViewHolder(
            ListItemModifyStockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val memberStock = getItem(position)
        (holder as MemberStockViewHolder).bind(memberStock, position)
    }

    inner class MemberStockViewHolder(
        private val binding: ListItemModifyStockBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MemberStock, position: Int) {
            binding.tvStockName.text = item.stock.name

            val isDollar = (item.currencyType == "DOLLAR")

            val benefit = if (isDollar) {
                item.current.won.amountPrice.toBigDecimal().minus(item.purchase.amountInWon?.toBigDecimal() ?: 0.toBigDecimal())
            } else {
                item.current.won.amountPrice.toBigDecimal() - item.purchase.amount.toBigDecimal()
            }
            if (benefit > 0.toBigDecimal()) {
                binding.tvBenefit.setTextColor(binding.root.resources.getColor(R.color.secondary_red, null))
                binding.tvBenefit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upward_red_16, 0, 0, 0)
            } else {
                binding.tvBenefit.setTextColor(binding.root.resources.getColor(R.color.secondary_blue, null))
                binding.tvBenefit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_downward_blue_16, 0, 0, 0)
            }
            binding.tvBenefit.text = "${NumberFormatUtil.numWithComma(benefit)}(${item.profitOrLoseRate}%)"

            if (isDollar) {
                binding.tvPrice.text = NumberFormatUtil.numWithComma(item.purchase.amountInWon?.toBigDecimal()?: 0.toBigDecimal())
            } else {
                binding.tvPrice.text = NumberFormatUtil.numWithComma(item.purchase.amount.toBigDecimal())
            }

            if (selectedIndex == position) {
                binding.root.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F4F4F4"))
            } else {
                binding.root.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#9E9DA9"))
            }

            binding.root.setOnClickListener {
                selectedIndex = position
                mOnItemSelectListener?.OnItemSelected(item)
                notifyDataSetChanged()
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