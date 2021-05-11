package com.depromeet.muyaho.ui.splash

import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel<SplashViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object ShowMain : ViewAction()
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            mainRepository.loadStockList()
            withContext(Dispatchers.Main) {
                actionSender.send(ViewAction.ShowMain)
            }
        }
    }
}