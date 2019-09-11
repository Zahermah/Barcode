package com.example.barcodetest.network

import com.example.barcodetest.model.Items
import com.example.barcodetest.model.ItemsList
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface getItemApi {

    @GET("trial/lookup?")
    fun getItemsData(@Query("upc") eancode: String): Call<ItemsList>

    @GET("trial/lookup?")
    fun getItemsListData(@Query("upc") array: ArrayList<String?>): Call<ItemsList>


    @GET("trial/lookup?")
    suspend fun getItems(@Query("upc") eancode: String): Deferred<Response<List<ItemsList>>>


}