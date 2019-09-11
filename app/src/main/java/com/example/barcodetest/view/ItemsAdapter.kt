package com.example.barcodetest.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.barcodetest.R
import com.example.barcodetest.ViewHolder.ItemsViewHolder
import com.example.barcodetest.model.Items


class ItemsAdapter(private var itemsList: ArrayList<Items>) :
    RecyclerView.Adapter<ItemsViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_view_fragment, viewGroup, false)
        addItems(itemsList)
        return ItemsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ItemsViewHolder, postion: Int) {
        viewHolder.bindView(itemsList[postion])
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun removeItem(pos: Int) {
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos, itemsList.size)
    }

    fun addItems(items: List<Items>) {
        itemsList.addAll(items)
    }

}
