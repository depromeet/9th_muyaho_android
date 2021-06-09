package com.depromeet.muyaho.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.depromeet.muyaho.R

class SmallAlertDialog(
        context: Context,
        val title: String,
        val message: String,
        val okText: String,
        val okClickListener: View.OnClickListener,
        val cancelText: String,
        val cancelClickListener: View.OnClickListener
): Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_small_alert)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textTitle = findViewById<TextView>(R.id.text_title)
        val textMessage = findViewById<TextView>(R.id.text_message)
        val btnOk = findViewById<TextView>(R.id.btn_ok)
        val btnCancel = findViewById<TextView>(R.id.btn_cancel)

        textTitle.text = title
        textMessage.text = message
        btnOk.text = okText
        btnCancel.text = cancelText

        btnOk.setOnClickListener {
            okClickListener.onClick(it)
            dismiss()
        }
        btnCancel.setOnClickListener {
            cancelClickListener.onClick(it)
            dismiss()
        }
    }
}