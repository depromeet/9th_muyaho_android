package com.depromeet.muyaho.ui.calc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeRateViewModel @Inject constructor(

) : BaseViewModel<IncomeRateViewModel.ViewAction>() {
    sealed class ViewAction : Action

    val holdingPrice = MutableLiveData<String>()
    val holdingCount = MutableLiveData<String>()
    val purchaseAmount = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            holdingCount.asFlow()
                .distinctUntilChanged()
                .combine(holdingPrice.asFlow()) { _count, _price ->
                    val count = _count.toInt()
                    val price = _price.toInt()
                    count * price
                }
                .collect { amount: Int ->
                    if (amount != 0) purchaseAmount.value = amount.toString()
                }
        }
    }
}