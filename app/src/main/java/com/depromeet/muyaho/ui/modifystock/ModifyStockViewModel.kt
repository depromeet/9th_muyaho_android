package com.depromeet.muyaho.ui.modifystock

import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ModifyStockViewModel @Inject constructor(

) : BaseViewModel<ModifyStockViewModel.ViewAction>() {
    sealed class ViewAction: Action {
    }
}