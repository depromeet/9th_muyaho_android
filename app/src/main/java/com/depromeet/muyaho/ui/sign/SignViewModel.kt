package com.depromeet.muyaho.ui.sign

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.repository.MainRepository
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val repo: MainRepository
) : BaseViewModel<SignViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object GoMain : ViewAction()
        object LoginKakao : ViewAction()
    }

    init {

    }

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "로그인 성공 ${token.accessToken}")
            loginWithKakao(token)
        }
    }

    fun onClickKakao() {
        viewModelScope.launch {
            actionSender.send(ViewAction.LoginKakao)
        }
    }

    private fun loginWithKakao(token: OAuthToken) = viewModelScope.launch {
        repo.loginKakao(token.accessToken)
            .body()
            ?.code
            .let { code ->
                when (code) {
                    "200" -> actionSender.send(ViewAction.GoMain)
                    "400" -> repo.signUpKakao(token.accessToken) // TODO name, profileUrl 가져와야함
                    else ->  Log.e(TAG, code ?: "null")
                }
            }
    }
}