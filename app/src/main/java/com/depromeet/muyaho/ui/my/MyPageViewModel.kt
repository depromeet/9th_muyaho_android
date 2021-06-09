package com.depromeet.muyaho.ui.my

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        object Logout : ViewAction()
        data class ShowError(val code: Int, val error: String) : ViewAction()
    }

    data class State(
        val title: CharSequence = ""
    )

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    init {
        viewModelScope.launch {
            val name = repo.getMember(accessToken).body()?.data?.name
                ?: throw NullPointerException("nickname was null")

            SpannableStringBuilder()
                .bold { append(name) }
                .append("님,\n환영합니다!")
                .let { _state.value = State(it) }
        }
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

    fun onClickLogout() = viewModelScope.launch {
        actionSender.send(ViewAction.Logout)
    }
}