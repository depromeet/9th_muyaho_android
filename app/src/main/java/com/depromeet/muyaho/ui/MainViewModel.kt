package com.depromeet.muyaho.ui

import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository
) : BaseViewModel<MainViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object ChangeMainText : ViewAction()
    }

    fun onClick() {
        viewModelScope.launch {
            actionSender.send(ViewAction.ChangeMainText)
        }
    }
}