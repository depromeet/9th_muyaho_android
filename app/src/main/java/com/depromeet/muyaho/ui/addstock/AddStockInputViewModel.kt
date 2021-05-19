package com.depromeet.muyaho.ui.addstock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddStockInputViewModel @Inject constructor(
) : BaseViewModel<AddStockInputViewModel.ViewAction>(){
    sealed class ViewAction : Action {

    }

    private val isDollarState: MutableLiveData<Boolean> = MutableLiveData()
    val isDollar: LiveData<Boolean> = isDollarState

    fun setDollarState(state: Boolean) {
        isDollarState.value = state
    }
}