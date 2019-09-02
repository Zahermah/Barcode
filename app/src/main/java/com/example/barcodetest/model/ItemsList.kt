package com.example.barcodetest.model

import com.google.gson.annotations.SerializedName

class ItemsList {
    @SerializedName("items")
    private val itemsList: ArrayList<Items>? = arrayListOf()

    fun getitemsArrayList(): ArrayList<Items>? {
        return itemsList
    }
}