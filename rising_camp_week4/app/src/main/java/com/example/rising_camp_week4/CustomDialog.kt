package com.example.rising_camp_week4

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton

class CustomDialog(context: Context) {
    private val dialog = Dialog(context)

    fun DialogShow(){

        dialog.setContentView(R.layout.manual_dialog)

        val btn = dialog.findViewById<AppCompatButton>(R.id.btn_ok)


        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()

        btn.setOnClickListener {
            dialog.dismiss()
        }
    }
}