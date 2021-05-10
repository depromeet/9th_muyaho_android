package com.depromeet.muyaho.ui.splash

import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : BaseViewModel<SplashViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object ShowMain : ViewAction()
    }

    init {
        viewModelScope.launch {
            delay(2000)
            actionSender.send(ViewAction.ShowMain)
        }
    }
}