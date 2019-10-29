package com.example.barcodetest.view.Utility

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.barcodetest.R

class ShowProgress(context: Context) : Dialog(context) {

    var dialog: Dialog? = null

    init {
        dialog = Dialog(context)
    }

    fun showDialog(context: Context) {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.progress_layout, null, false)
        dialog?.setCancelable(false)
        dialog?.setContentView(dialogView)
        dialog?.show()
    }


    fun dismissPopup() = dialog.let { dialog?.dismiss() }

}