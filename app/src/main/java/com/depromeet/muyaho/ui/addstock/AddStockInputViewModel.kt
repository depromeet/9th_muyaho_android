package com.depromeet.muyaho.ui.addstock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class AddStockInputViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel<AddStockInputViewModel.ViewAction>(){
    sealed class ViewAction : Action {

    }
    var stockType: String = StockType.Domestic.full_name

    val isProcessing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPostComplete: MutableLiveData<Boolean> = MutableLiveData(false)

    fun postMemberStock(stockId: Int, purchasePrice: Float, quantity: Float, purchaseTotalPrice: Float) {
        isProcessing.value = true

        viewModelScope.launch(Dispatchers.Default) {
            val result = if (stockType == StockType.Overseas.full_name) {
                mainRepository.postMemberStock(stockId, purchasePrice, quantity, "DOLLAR", purchaseTotalPrice)
            } else {
                mainRepository.postMemberStock(stockId, purchasePrice, quantity, "WON", purchasePrice * quantity)
            }

            mainRepository.getMemberStockStatus()

            withContext(Dispatchers.Main) {
                isProcessing.value = false
                isPostComplete.value = result
            }
        }
    }
}