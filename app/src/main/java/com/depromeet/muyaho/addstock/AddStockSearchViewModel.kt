package com.depromeet.muyaho.addstock

import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddStockSearchViewModel @Inject constructor(

) : BaseViewModel<AddStockSearchViewModel.ViewAction>(){
    sealed class ViewAction : Action {

    }
}