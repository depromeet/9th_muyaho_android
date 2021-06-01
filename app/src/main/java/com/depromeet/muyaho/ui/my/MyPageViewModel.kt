package com.depromeet.muyaho.ui.my

import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : BaseViewModel<MyPageViewModel.ViewAction>() {
    sealed class ViewAction : Action {

    }

    init {

    }

    fun onClickLeave() {

    }
}