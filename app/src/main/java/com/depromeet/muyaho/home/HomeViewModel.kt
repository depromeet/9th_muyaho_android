package com.depromeet.muyaho.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.depromeet.muyaho.base.Action
import com.depromeet.muyaho.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : BaseViewModel<HomeViewModel.ViewAction>() {

    val title: LiveData<String> = liveData {
        emit("주린이 눈물님은\n2,700,000원 넣고\n684,542원 잃었어요!")
    }
    val budgetPrice: LiveData<String> = liveData {
        emit("2,004,002")
    }
    val budgetPercent: LiveData<String> = liveData {
        emit("-12.4%")
    }
    val profitPrice: LiveData<String> = liveData {
        emit("2,000,000")
    }
    val profitPercent: LiveData<String> = liveData {
        emit("20")
    }

    sealed class ViewAction : Action {

    }

    init {

    }
}