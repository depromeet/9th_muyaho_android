package com.depromeet.muyaho.ui.calc

import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalcViewModel @Inject constructor(

) : BaseViewModel<CalcViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object ShowHistory : ViewAction()
        object ShowRevenueRate : ViewAction()
        object ShowWater : ViewAction()
    }

    init {
        viewModelScope.launch {
            actionSender.send(ViewAction.ShowHistory)
        }
    }

    fun onClickHistory() = viewModelScope.launch {
        actionSender.send(ViewAction.ShowHistory)
    }

    fun onClickRevenueRate() = viewModelScope.launch {
        actionSender.send(ViewAction.ShowRevenueRate)
    }

    fun onClickWater() = viewModelScope.launch {
        actionSender.send(ViewAction.ShowWater)
    }
}