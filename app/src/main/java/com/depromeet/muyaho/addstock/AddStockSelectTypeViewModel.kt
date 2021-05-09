package com.depromeet.muyaho.addstock

import android.widget.RadioGroup
import androidx.lifecycle.viewModelScope
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStockSelectTypeViewModel @Inject constructor(

) : BaseViewModel<AddStockSelectTypeViewModel.ViewAction>() {
    sealed class ViewAction : Action {
        object startSearchStockFragment: ViewAction()
    }

    var selectedStockType = StockType.Domestic

    enum class StockType(
        name: String
    ) {
        Domestic("Domestic"),
        Overseas("Overseas"),
        Virtual("Virtual")
    }

    fun onSearchBtnClick() {
        viewModelScope.launch {
            actionSender.send(ViewAction.startSearchStockFragment)
        }
    }

    val stockTypeCheckChangeListener = object : RadioGroup.OnCheckedChangeListener{
        override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
            when (p1) {
                R.id.rb_domestic_stock -> selectedStockType = StockType.Domestic
                R.id.rb_overseas_stock -> selectedStockType = StockType.Overseas
                R.id.rb_virtual_currency -> selectedStockType = StockType.Virtual
            }
        }
    }
}