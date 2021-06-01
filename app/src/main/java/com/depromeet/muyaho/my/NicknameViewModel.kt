package com.depromeet.muyaho.my

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.other.Constants.CODE_200_OK
import com.depromeet.muyaho.other.Constants.CODE_409_CONFLICT
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val repo: MainRepository
) : BaseViewModel<NicknameViewModel.ViewAction>() {

    sealed class ViewAction : Action {
        object GoMain : ViewAction()
    }

    @Bindable
    val nickname = MutableLiveData<String>()
    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    data class State(
        val enabled: Boolean = false,
        val nickName: String = "",
        val isConflict: Boolean = false
    )

    init {
        viewModelScope.launch {
            nickname.asFlow()
                .debounce(500) // Wait 500ms
                .distinctUntilChanged() // Ignore same value (This is the default operator)
                .collect { nickname ->
                    val enabled = nickname != null && nickname.isNotEmpty() && nickname.length < 5
                    checkConflict(enabled, nickname)
                }
        }
    }

    fun onClickStart() = viewModelScope.launch {
        actionSender.send(ViewAction.GoMain)
    }

    private fun checkConflict(enabled: Boolean, nickname: String) = viewModelScope.launch {
        when (repo.checkNickName(nickname).code()) {
            CODE_200_OK -> _state.value = _state.value?.copy(enabled = enabled, nickName = nickname)
            CODE_409_CONFLICT -> _state.value = _state.value?.copy(isConflict = true)
        }
    }
}