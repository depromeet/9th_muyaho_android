package com.depromeet.muyaho.ui.calc

import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalcViewModel @Inject constructor(

) : BaseViewModel<CalcViewModel.ViewAction>() {
    sealed class ViewAction : Action {

    }

    init {

    }
}