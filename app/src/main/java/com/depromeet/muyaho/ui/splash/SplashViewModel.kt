package com.depromeet.muyaho.ui.splash

import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel<SplashViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object CheckAutoLogin : ViewAction()
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            mainRepository.loadStockList(StockType.Domestic.full_name)
            mainRepository.loadStockList(StockType.Overseas.full_name)
            mainRepository.loadStockList(StockType.Bitcoin.full_name)
            mainRepository.getExchangeRate()
            delay(2000L)
            actionSender.send(ViewAction.CheckAutoLogin)
        }
    }
}