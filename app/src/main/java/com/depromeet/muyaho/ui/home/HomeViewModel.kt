package com.depromeet.muyaho.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.api.ApiDataModel
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.data.MemberStockStatus
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel<HomeViewModel.ViewAction>() {
    sealed class ViewAction : Action {

    }

    init {
        getMemberStockStatus()
    }

    val memberStockStatus: MutableLiveData<MemberStockStatus> = MutableLiveData()

    fun getMemberStockStatus() {
        viewModelScope.launch {
            val data = mainRepository.getMemberStockStatus()
            withContext(Dispatchers.Main) {
                memberStockStatus.value = data
            }
        }
    }

    fun getMemberStockStatusCache() {
        viewModelScope.launch {
            val data = mainRepository.getMemberStockStatusCache()
            withContext(Dispatchers.Main) {
                memberStockStatus.value = data
            }
        }
    }
}