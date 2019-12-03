package com.example.barcodetest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.barcodetest.view.LoginActivity
import com.google.firebase.FirebaseApp

class WelcomeSplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        FirebaseApp.initializeApp(this)
    }
}