package com.depromeet.muyaho.util

import android.view.View
import android.widget.RadioGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("setCheckChangeListener")
fun bindSetCheckChangeListener(
    radioGroup: RadioGroup,
    listener: RadioGroup.OnCheckedChangeListener
) {
    radioGroup.setOnCheckedChangeListener(listener)
}

@BindingAdapter("visibleIf")
fun View.setVisibleIf(value: Boolean) {
    isVisible = value
}

@BindingAdapter("invisibleIf")
fun View.setInvisibleIf(value: Boolean) {
    isInvisible = value
}

@BindingAdapter("goneIf")
fun View.setGoneIf(value: Boolean) {
    isGone = value
}