package com.depromeet.muyaho.my

import androidx.lifecycle.*
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.other.Constants.CODE_200_OK
import com.depromeet.muyaho.other.Constants.CODE_409_CONFLICT
import com.depromeet.muyaho.other.Extra.KEY_PROFILE_URL
import com.depromeet.muyaho.other.Extra.KEY_TOKEN
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val repo: MainRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NicknameViewModel.ViewAction>() {

    sealed class ViewAction : Action {
        object GoMain : ViewAction()
        data class ShowError(val code: Int, val error: String) : ViewAction()
    }

    private val token = savedStateHandle.get<String>(KEY_TOKEN)
    private val profileUrl = savedStateHandle.get<String>(KEY_PROFILE_URL)
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
                .collect { nickname -> checkConflict(nickname) }
        }
    }

    fun onClickStart() = viewModelScope.launch {
        val token = token ?: throw NullPointerException("token was null")
        val name = _state.value?.nickName ?: return@launch
        checkConflict(name)
        if (state.value?.enabled == true)
            repo.signUpKakao(SignUpBody(token, name, profileUrl))
                .let { response ->
                    if (response.isSuccessful) actionSender.send(ViewAction.GoMain)
                    else actionSender.send(
                        ViewAction.ShowError(response.code(), response.errorBody().toString())
                    )
                }
    }

    private fun checkConflict(nickname: String) = viewModelScope.launch {
        val enabled = nickname.isNotEmpty() && nickname.length <= 5
        if (!enabled) {
            _state.value = State(enabled = enabled, nickName = nickname, isConflict = false)
            return@launch
        }
        val (isAvailable, isConflict) = when (repo.checkNickName(nickname).code()) {
            CODE_200_OK -> true to false
            CODE_409_CONFLICT -> false to true
            else -> false to false
        }
        _state.value = State(enabled = isAvailable, nickName = nickname, isConflict = isConflict)

    }
}