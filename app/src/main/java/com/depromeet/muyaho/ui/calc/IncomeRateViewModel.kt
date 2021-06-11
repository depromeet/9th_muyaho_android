package com.depromeet.muyaho.ui.calc

import androidx.lifecycle.*
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeRateViewModel @Inject constructor(

) : BaseViewModel<IncomeRateViewModel.ViewAction>() {
    sealed class ViewAction : Action

    val holdingPrice = MutableLiveData<String>()
    val holdingCount = MutableLiveData<String>()
    val purchaseAmount = MutableLiveData<String>()
    val targetIncomeRate = MutableLiveData<String>()
    val predictAmount = MutableLiveData<String>()
    val visiblePredictView: LiveData<Boolean>
        get() = predictAmount.map { it.isNotEmpty() }

    init {
        viewModelScope.launch {
            holdingCount.asFlow()
                .distinctUntilChanged()
                .combine(holdingPrice.asFlow()) { _count, _price ->
                    val decimal = if (_count.isNotEmpty() && _price.isNotEmpty()) {
                        val count = _count.toDouble()
                        val price = _price.toDouble()
                        (count * price).toBigDecimal()
                    } else (0.0).toBigDecimal()
                    decimal.toInt()
                }.collect { amount ->
                    if (amount > 0) {
                        val predict = amount.toString()
                        purchaseAmount.value = predict
                    }
                }

        }
        // TODO 1000% 이하까지 구현
        targetIncomeRate.asFlow()
            .distinctUntilChanged()
            .combine(purchaseAmount.asFlow()) { rate, amount ->
                if (amount.isNotEmpty() && rate.isNotEmpty()) (amount.toDouble() * (rate.toDouble() / 100))
                else (0.0)
            }
            .onEach { total ->
                val amount = String.format("%d", total.toLong())
                predictAmount.value = amount
            }.launchIn(viewModelScope)
    }
}