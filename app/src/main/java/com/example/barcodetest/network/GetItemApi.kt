package com.example.barcodetest.network

import com.example.barcodetest.model.ItemsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetItemApi {

    @GET("trial/lookup?")
    fun getItemsData(@Query("upc") eancode: String): Call<ItemsList>

    @GET("trial/lookup?")
    fun getItemsListData(@Query("upc") array: ArrayList<String?>): Call<ItemsList>
}