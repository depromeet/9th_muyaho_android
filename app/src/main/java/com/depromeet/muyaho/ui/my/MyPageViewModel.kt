package com.depromeet.muyaho.ui.my

import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.repository.MainRepository
import com.depromeet.muyaho.util.PreferenceUtil.accessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val repo: MainRepository
) : BaseViewModel<MyPageViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object GoSplash : ViewAction()
        data class ShowError(val code: Int, val error: String) : ViewAction()
    }

    fun onClickLeave() = viewModelScope.launch {
        repo.deleteMember(accessToken)
            .let { response ->
                if (response.isSuccessful) actionSender.send(ViewAction.GoSplash)
                else actionSender.send(
                    ViewAction.ShowError(
                        response.code(),
                        response.errorBody().toString()
                    )
                )
            }
    }
}