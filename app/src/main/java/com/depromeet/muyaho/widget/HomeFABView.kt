package com.depromeet.muyaho.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.depromeet.muyaho.R
import com.depromeet.muyaho.databinding.ViewHomeFabBinding

class HomeFABView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {
    private val binding: ViewHomeFabBinding by lazy {
        ViewHomeFabBinding.inflate(LayoutInflater.from(context))
    }

    var isClicked = false
    init {
        addView(binding.root)
        binding.fabMain.setOnClickListener {
            toggle()
        }
    }

    fun toggle() {
        isClicked = !isClicked
        refreshUI(isClicked)
    }

    fun refreshUI(btnClicked: Boolean) {
        if (btnClicked) {
            binding.fabMain.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white_2, null))
            binding.fabMain.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.primary_blue, null))
            binding.fabMain.setImageResource(R.drawable.ic_cancel_blue_32)

            binding.fabSubModify.visibility = View.VISIBLE
            binding.fabSubNew.visibility = View.VISIBLE
            binding.tvSubModify.visibility = View.VISIBLE
            binding.tvSubNew.visibility = View.VISIBLE

            binding.root.setBackgroundColor(Color.parseColor("#CC000000"))
            binding.root.isClickable = true
        } else {
            binding.fabMain.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.primary_blue, null))
            binding.fabMain.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.white_2, null))
            binding.fabMain.setImageResource(R.drawable.ic_pencil_white_32)

            binding.fabSubModify.visibility = View.GONE
            binding.fabSubNew.visibility = View.GONE
            binding.tvSubModify.visibility = View.GONE
            binding.tvSubNew.visibility = View.GONE

            binding.root.setBackgroundColor(Color.parseColor("#00000000"))
            binding.root.isClickable = false
        }
    }
}