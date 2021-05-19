package com.depromeet.muyaho.ui.modifystock

import android.widget.RadioGroup
import androidx.lifecycle.*
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.data.MemberStock
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyStockSelectViewModel @Inject constructor(
    mainRepository: MainRepository
) : BaseViewModel<ModifyStockSelectViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object CloseActivity: ViewAction()
    }

    val stockType: MutableLiveData<String> = MutableLiveData()

    val stockList: LiveData<List<MemberStock>> = stockType.switchMap {
        liveData {
            mainRepository.getMemberStock(it).collect {
                emit(it)
            }
        }
    }

    fun onCloseBtnClick() {
        viewModelScope.launch {
            actionSender.send(ViewAction.CloseActivity)
        }
    }

    val stockTypeCheckChangeListener = object : RadioGroup.OnCheckedChangeListener{
        override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
            when (p1) {
                R.id.rb_domestic_stock -> stockType.value = StockType.Domestic.full_name
                R.id.rb_overseas_stock -> stockType.value = StockType.Overseas.full_name
                R.id.rb_virtual_currency -> stockType.value = StockType.Bitcoin.full_name
            }
        }
    }
}