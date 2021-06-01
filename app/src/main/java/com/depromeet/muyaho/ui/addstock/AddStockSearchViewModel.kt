package com.depromeet.muyaho.ui.addstock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import com.depromeet.muyaho.data.Stock
import com.depromeet.muyaho.data.StockType
import com.depromeet.muyaho.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class AddStockSearchViewModel @Inject constructor(
    mainRepository: MainRepository
) : BaseViewModel<AddStockSearchViewModel.ViewAction>(){
    sealed class ViewAction : Action {

    }

    var stockType: String = StockType.Domestic.full_name
    private val searchWord: MutableLiveData<String> = MutableLiveData()

    val stocks: LiveData<List<Stock>> = searchWord.switchMap { word ->
        liveData {
            mainRepository.getStockList(stockType, word).collect {
                emit(it)
            }
        }
    }

    fun setSearchWord(word: String) {
        searchWord.value = word
    }
}