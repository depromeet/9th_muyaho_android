package com.depromeet.muyaho.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import com.depromeet.muyaho.R
import com.depromeet.muyaho.databinding.EditTextPriceBinding
import com.depromeet.muyaho.util.NumberFormatUtil

class PriceEditText@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {
    private val binding: EditTextPriceBinding by lazy {
        EditTextPriceBinding.inflate(LayoutInflater.from(context))
    }

    var price: String = ""
    var priceType: PriceType = PriceType.WON

    enum class PriceType {
        WON,
        DOLLAR,
        COUNT
    }

    var mOnEditCompleteListener: OnEditCompleteListener? = null
    interface OnEditCompleteListener {
        fun OnComplete()
    }

    private val imm: InputMethodManager by lazy {
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    init {
        addView(binding.root)
        binding.etPrice.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.viewUnderline.setBackgroundColor(resources.getColor(R.color.primary_blue_fade, null))
                if (binding.etPrice.text.toString().isBlank()) {
                    binding.ivDelete.visibility = View.GONE
                } else {
                    binding.ivDelete.visibility = View.VISIBLE
                }

                binding.etPrice.setText(price)
                binding.tvPrice.visibility = View.GONE
            } else {
                binding.viewUnderline.setBackgroundColor(resources.getColor(R.color.gray_60, null))
                binding.ivDelete.visibility = View.GONE

                price = binding.etPrice.text.toString()
                binding.etPrice.setText("")

                updateFormatPrice()
                binding.tvPrice.visibility = View.VISIBLE

                mOnEditCompleteListener?.OnComplete()
            }
        }
        binding.etPrice.addTextChangedListener {
            updateEditTextColor()
        }

        binding.ivDelete.setOnClickListener {
            binding.etPrice.setText("0")
            updateEditTextColor()
        }

        binding.etPrice.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_ENTER) {
                imm.hideSoftInputFromWindow(binding.etPrice.windowToken, 0)
                binding.etPrice.clearFocus()
                true
            } else {
                false
            }
        }

        // first exec
        binding.etPrice.setText("0")
        updateFormatPrice()
    }

    fun updateEditTextColor() {
        if (binding.etPrice.text.toString().isBlank()) {
            binding.etPrice.setTextColor(resources.getColor(R.color.gray_40, null))
        } else {
            binding.etPrice.setTextColor(resources.getColor(R.color.white_2, null))
        }
    }

    fun updateFormatPrice() {
        val price = if (this.price.isNullOrBlank()) {
            "0"
        } else {
            this.price
        }

        val formatPrice = when (priceType) {
            PriceType.WON -> NumberFormatUtil.numWithComma(price.toFloat())
            PriceType.DOLLAR -> NumberFormatUtil.numWithDollar(price.toFloat())
            PriceType.COUNT -> NumberFormatUtil.numWithCount(price.toFloat())
        }
        binding.tvPrice.text = formatPrice
    }
}