package com.example.barcodetest.model

import com.google.gson.annotations.SerializedName

data class EanNumbers(
    @SerializedName("EAN")
    var EAN: String
)