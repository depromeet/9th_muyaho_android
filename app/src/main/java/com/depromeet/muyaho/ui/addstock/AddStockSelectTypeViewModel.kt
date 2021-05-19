package com.depromeet.muyaho.ui.addstock

import android.widget.RadioGroup
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.data.StockType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStockSelectTypeViewModel @Inject constructor(

) : BaseViewModel<AddStockSelectTypeViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object StartSearchStockFragment: ViewAction()
        object CloseActivity: ViewAction()
    }

    var selectedStockType = StockType.Domestic

    fun onSearchBtnClick() {
        viewModelScope.launch {
            actionSender.send(ViewAction.StartSearchStockFragment)
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
                R.id.rb_domestic_stock -> selectedStockType = StockType.Domestic
                R.id.rb_overseas_stock -> selectedStockType = StockType.Overseas
                R.id.rb_virtual_currency -> selectedStockType = StockType.Bitcoin
            }
        }
    }
}