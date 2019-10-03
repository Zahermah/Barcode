package com.example.barcodetest.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.upcitemdb.com/prod/"
class RetrofitInstance {

    fun getRetrofitInstance(): Retrofit {
        var retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }
}