package com.depromeet.muyaho.ui.calc

import androidx.lifecycle.*
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class IncomeRateViewModel @Inject constructor(

) : BaseViewModel<IncomeRateViewModel.ViewAction>() {
    sealed class ViewAction : Action

    val holdingPrice = MutableLiveData<String>()
    val holdingCount = MutableLiveData<String>()
    val purchaseAmount = MutableLiveData<String>()
    val targetIncomeRate = MutableLiveData<String>()
    private val _predictAmount = MutableLiveData<Double>()
    val predictAmount: LiveData<String>
        get() = _predictAmount
            .map { it.toString() }
            .map { it.substring(0 until it.length - 2) }
    val visiblePredictView: LiveData<Boolean>
        get() = predictAmount.map { it.isNotEmpty() }

    init {
        viewModelScope.launch {
            holdingCount.asFlow()
                .distinctUntilChanged()
                .combine(holdingPrice.asFlow()) { _count, _price ->
                    val count = _count.toInt()
                    val price = _price.toInt()
                    count * price
                }.collect { amount ->
                    if (amount != 0) purchaseAmount.value = amount.toString()
                }

        }
        // TODO 1000% 이하까지 구현
        targetIncomeRate.asFlow()
            .distinctUntilChanged()
            .combine(purchaseAmount.asFlow()) { rate, amount ->
                (amount.toDouble() * (rate.toDouble() / 100))
            }
            .onEach { total ->
                _predictAmount.value = ceil(total)
            }.launchIn(viewModelScope)
    }
}