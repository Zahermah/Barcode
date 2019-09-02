package com.example.barcodetest.model

import com.google.gson.annotations.SerializedName

class EanList {
    @SerializedName("EANNUMBER")
    private val eanList: ArrayList<EanNumbers>? = null

    fun getEANArrayList(): ArrayList<EanNumbers>? {
        return eanList
    }

}