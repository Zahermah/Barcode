package com.example.barcodetest.view.Utility

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.example.barcodetest.R

class ShowProgress(context: Context) : Dialog(context) {

    var dialog: Dialog? = null
    var imageView: ImageView? = null

    init {
        dialog = Dialog(context)
    }

    fun showDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.progress_layout, null, false)
        imageView = dialogView.findViewById(R.id.glideImage)
        val imagebus = DrawableImageViewTarget(imageView)
        imageView?.let {
            Glide.with(context).load(R.drawable.verstappen).into(imagebus)
        }

        dialog?.setCancelable(false)
        dialog?.setContentView(dialogView)
        dialog?.show()

    }

    fun dismissPopup() = dialog.let { dialog?.dismiss() }

}