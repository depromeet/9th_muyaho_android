package com.depromeet.muyaho.ui.modifystock

import android.widget.RadioGroup
import androidx.lifecycle.*
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ModifyStockSelectViewModel @Inject constructor(
    val mainRepository: MainRepository
) : BaseViewModel<ModifyStockSelectViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object CloseActivity: ViewAction()
    }

    val stockList: MutableLiveData<List<MemberStock>> = MutableLiveData()

    fun getStockList(stockType: String) {
        viewModelScope.launch {
            val data = mainRepository.getMemberStockStatusCache()
            withContext(Dispatchers.Main) {
                when (stockType) {
                    StockType.Domestic.full_name -> {
                        stockList.value = data?.overview?.domesticStocks
                    }
                    StockType.Overseas.full_name -> {
                        stockList.value = data?.overview?.foreignStocks
                    }
                    StockType.Bitcoin.full_name -> {
                        stockList.value = data?.overview?.bitCoins
                    }
                }
            }
        }
    }

    fun onCloseBtnClick() {
        viewModelScope.launch {
            actionSender.send(ViewAction.CloseActivity)
        }
    }

    val stockTypeCheckChangeListener = object : RadioGroup.OnCheckedChangeListener{
        override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
            when (p1) {
                R.id.rb_domestic_stock -> getStockList(StockType.Domestic.full_name)
                R.id.rb_overseas_stock -> getStockList(StockType.Overseas.full_name)
                R.id.rb_virtual_currency -> getStockList(StockType.Bitcoin.full_name)
            }
        }
    }
}