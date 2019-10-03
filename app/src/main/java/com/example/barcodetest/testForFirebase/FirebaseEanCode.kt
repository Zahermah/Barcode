package com.example.barcodetest.testForFirebase

data class FirebaseEanCode(var eanId: String, var eanValue: String) {
    constructor() : this("", "")
}