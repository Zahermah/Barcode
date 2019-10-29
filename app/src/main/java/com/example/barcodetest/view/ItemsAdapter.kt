package com.example.barcodetest.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodetest.R
import com.example.barcodetest.ViewHolder.ItemsViewHolder
import com.example.barcodetest.model.Items


class ItemsAdapter(private var itemsList: ArrayList<Items>) :
    RecyclerView.Adapter<ItemsViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_view_fragment, viewGroup, false)

        return ItemsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ItemsViewHolder, postion: Int) {
        viewHolder.bindView(itemsList[postion])
    }

    override fun getItemCount() = itemsList.count()


    fun removeItem(pos: Int) {
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos, itemsList.size)
    }
}