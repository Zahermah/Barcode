package com.example.barcodetest.testForFirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.barcodetest.R

class firebaseEanAdapter(
    val Adaptercontext: Context,
    val layoutresId: Int,
    val firebaseEanCode: List<FirebaseEanCode>
) :
    ArrayAdapter<FirebaseEanCode>(Adaptercontext, layoutresId, firebaseEanCode) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(Adaptercontext)
        val view: View = layoutInflater.inflate(layoutresId, null)
        val textView = view.findViewById<TextView>(R.id.eanTextView)
        val eanCode = firebaseEanCode[position]
        textView.text = eanCode.eanValue
        return view
    }
}