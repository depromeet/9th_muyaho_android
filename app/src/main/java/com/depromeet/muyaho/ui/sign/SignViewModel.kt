package com.depromeet.muyaho.ui.sign

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.api.ApiHelperImpl
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.body.SignUpBody
import com.depromeet.muyaho.other.Constants
import com.depromeet.muyaho.other.Constants.CODE_200_OK
import com.depromeet.muyaho.other.Constants.CODE_404_NOT_FOUND
import com.depromeet.muyaho.repository.MainRepository
import com.depromeet.muyaho.util.PreferenceUtil
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.Profile
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

    fun onClickKakao() {
        viewModelScope.launch { actionSender.send(ViewAction.LoginKakao) }
    }

    fun loginWithKakao(token: OAuthToken, profile: Profile?) = viewModelScope.launch {
        val nickname = requireNotNull(profile?.nickname)
        val thumbnailImageUrl = requireNotNull(profile?.thumbnailImageUrl)
        val body = SignUpBody(token.accessToken, nickname, thumbnailImageUrl)

        repo.loginKakao(token.accessToken)
            .let { result ->
                when (result.code()) {
                    CODE_200_OK -> {
                        PreferenceUtil.AccessToken =
                            result.body()?.data?.sessionId ?: Constants.TEST_SESSION_ID
                        actionSender.send(ViewAction.GoMain)
                    }
                    CODE_404_NOT_FOUND -> signUpWithKakao(body)
                    else -> Log.e(TAG, "code ${result.code()} is not expected")
                }
            }
    }

    private fun signUpWithKakao(body: SignUpBody) = viewModelScope.launch {
        val result = repo.signUpKakao(body)
        when (result.code()) {
            CODE_200_OK -> actionSender.send(ViewAction.GoMain)
            else -> Log.e(TAG, "code ${result.code()} is not expected")
        }
    }
}