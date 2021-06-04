package com.depromeet.muyaho.ui.home

import androidx.lifecycle.*
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.data.MemberStockStatus
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeDetailContentViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel<HomeDetailContentViewModel.ViewAction>() {
    sealed class ViewAction : Action {

    }

    var stockType: String? = ""

    val isProcessing: MutableLiveData<Boolean> = MutableLiveData(false)

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

    fun deleteMemberStock(item: MemberStock) {
        isProcessing.value = true

        viewModelScope.launch(Dispatchers.Default) {
            val result = mainRepository.deleteMemberStock(item.memberStockId)

            if (result > 0) {
                getMemberStockStatus()
            }

            withContext(Dispatchers.Main) {
                isProcessing.value = false
            }
        }
    }
}