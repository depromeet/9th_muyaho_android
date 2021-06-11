package com.depromeet.muyaho.ui.calc

import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel<ShareViewModel.ViewAction>(){
    sealed class ViewAction : Action {

    }
}