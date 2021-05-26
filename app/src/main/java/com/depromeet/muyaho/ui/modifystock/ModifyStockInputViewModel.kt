package com.depromeet.muyaho.ui.modifystock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ModifyStockInputViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel<ModifyStockInputViewModel.ViewAction>(){
    sealed class ViewAction : Action {

    }

    private val isDollarState: MutableLiveData<Boolean> = MutableLiveData()
    val isDollar: LiveData<Boolean> = isDollarState

    val isProcessing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPutComplete: MutableLiveData<Boolean> = MutableLiveData(false)

    fun setDollarState(state: Boolean) {
        isDollarState.value = state
    }

    fun putMemberStock(memberStockId: Int, purchasePrice: Int, quantity: Int) {
        isProcessing.value = true

        viewModelScope.launch(Dispatchers.Default) {
            val result = mainRepository.putMemberStock(memberStockId, purchasePrice, quantity, if(isDollarState.value == true) "DOLLAR" else "WON")

            withContext(Dispatchers.Main) {
                isProcessing.value = false
                isPutComplete.value = result
            }
        }
    }
}