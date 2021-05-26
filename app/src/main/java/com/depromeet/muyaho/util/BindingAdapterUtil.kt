package com.depromeet.muyaho.util

import android.widget.RadioGroup
import androidx.databinding.BindingAdapter

@BindingAdapter("setCheckChangeListener")
fun bindSetCheckChangeListener(radioGroup: RadioGroup, listener: RadioGroup.OnCheckedChangeListener) {
    radioGroup.setOnCheckedChangeListener(listener)
}