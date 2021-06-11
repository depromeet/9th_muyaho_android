package com.depromeet.muyaho.ui.calc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        object ShowIncomeRate : ViewAction()
        object ShowWater : ViewAction()
    }

    data class State(
        val currentTab: Tab = Tab.HISTORY
    )

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun init() {
        onClickIncomeRate()
    }

    fun onClickHistory() = viewModelScope.launch {
//         actionSender.send(ViewAction.ShowHistory)
//         _state.value = State(currentTab = Tab.HISTORY)
    }

    fun onClickIncomeRate() = viewModelScope.launch {
        actionSender.send(ViewAction.ShowIncomeRate)
        _state.value = State(currentTab = Tab.INCOME_RATE)
    }

    fun onClickWater() = viewModelScope.launch {
        actionSender.send(ViewAction.ShowWater)
        _state.value = State(currentTab = Tab.WATER)
    }

    enum class Tab {
        HISTORY,
        INCOME_RATE,
        WATER
    }
}