package com.depromeet.muyaho.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.depromeet.muyaho.api.ApiDataModel
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.data.MemberStockStatus
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel<HomeViewModel.ViewAction>() {
    sealed class ViewAction : Action {

    }

    val memberStockStatus: LiveData<MemberStockStatus> = liveData {
        mainRepository.getMemberStockStatusCache().collect {
            emit(it)
        }

        mainRepository.getMemberStockStatus().collect {
            emit(it)
        }
    }

    init {

    }
}