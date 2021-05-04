package com.depromeet.muyaho.addstock

import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddStockSelectTypeViewModel @Inject constructor(

) : BaseViewModel<AddStockSelectTypeViewModel.ViewAction>() {
    sealed class ViewAction : Action {

    }
}