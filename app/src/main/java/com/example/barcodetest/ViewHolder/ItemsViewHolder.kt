package com.example.barcodetest.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.barcodetest.model.Items
import kotlinx.android.synthetic.main.card_view_fragment.view.*

class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(item: Items) {
        itemView.Item_brand.text = item.brand
        itemView.Item_title.text = item.title
        itemView.Item_description.text = item.description


    }
}