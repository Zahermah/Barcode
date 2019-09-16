package com.example.barcodetest.model

class cachedEanList {

    val maxMemory: Long = Runtime.getRuntime().maxMemory() / 1024
    val cacheSize: Long = maxMemory / 8



}