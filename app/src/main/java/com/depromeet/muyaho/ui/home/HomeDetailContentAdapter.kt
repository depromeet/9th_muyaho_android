package com.depromeet.muyaho.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.depromeet.muyaho.data.MemberStockStatus
import com.depromeet.muyaho.data.StockType

class HomeDetailContentAdapter(fragment: Fragment, val memberStockStatus: MemberStockStatus) : FragmentStateAdapter(fragment) {

    val fragmentMap: HashMap<Int, Fragment> = HashMap()

    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
        val fragment = HomeDetailContentFragment()
        fragment.arguments = Bundle().apply {
            val memberStockList = when (position) {
                0 -> memberStockStatus.overview.domesticStocks.toTypedArray()
                1 -> memberStockStatus.overview.foreignStocks.toTypedArray()
                2 -> memberStockStatus.overview.bitCoins.toTypedArray()
                else -> null
            }
            putParcelableArray(HomeDetailContentFragment.ARG_MEMBER_STOCK_LIST_KEY, memberStockList)
            val stockType = when (position) {
                0 -> StockType.Domestic.full_name
                1 -> StockType.Overseas.full_name
                2 -> StockType.Bitcoin.full_name
                else -> ""
            }
            putString(HomeDetailContentFragment.ARG_STOCK_TYPE, stockType)
        }
        fragmentMap[position] = fragment
        return fragment
    }
}