package com.depromeet.muyaho.ui.addstock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.muyaho.data.Stock
import com.depromeet.muyaho.databinding.ListItemSearchResultBinding

class AddStockSearchListAdapter: ListAdapter<Stock, RecyclerView.ViewHolder>(StockDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockViewHolder(
            ListItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val stock = getItem(position)
        (holder as StockViewHolder).bind(stock)
    }

    class StockViewHolder(
        private val binding: ListItemSearchResultBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Stock) {
            binding.apply {
                tvStockName.text = item.name
                executePendingBindings()
            }
        }
    }
}

private class StockDiffCallback: DiffUtil.ItemCallback<Stock>() {
    override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return oldItem == newItem
    }
}