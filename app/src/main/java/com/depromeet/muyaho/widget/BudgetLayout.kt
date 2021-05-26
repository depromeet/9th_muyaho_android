package com.depromeet.muyaho.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.depromeet.muyaho.databinding.LayoutBudgetBinding

class BudgetLayout@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutBudgetBinding by lazy {
        LayoutBudgetBinding.inflate(LayoutInflater.from(context))
    }

    init {
        addView(binding.root)
    }
}