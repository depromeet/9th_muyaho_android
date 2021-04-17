package com.depromeet.muyaho

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object ChangeMainText : ViewAction()
    }

    fun onClick() {
        Log.d("!@#", "f onClick")
        viewModelScope.launch {
            Log.d("!@#", "onClick")
            actionSender.send(ViewAction.ChangeMainText)
        }
    }
}