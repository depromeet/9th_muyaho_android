package com.depromeet.muyaho.ui.addstock

import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddStockViewModel @Inject constructor(

) : BaseViewModel<AddStockViewModel.ViewAction>() {
    sealed class ViewAction : Action {
    }
}