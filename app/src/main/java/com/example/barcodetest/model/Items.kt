package com.example.barcodetest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("brand")
    @Expose
    var brand: String,
    @SerializedName("description")
    @Expose
    var description: String
    /*
    @SerializedName("images")
    var images: Array<String> = arrayOf()
    */
)
