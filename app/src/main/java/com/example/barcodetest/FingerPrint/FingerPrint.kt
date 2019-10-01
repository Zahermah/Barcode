package com.example.barcodetest.FingerPrint

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import com.example.barcodetest.MainActivity
import com.example.barcodetest.R
import com.example.barcodetest.view.LoginActivity
import java.util.concurrent.Executors

class FingerPrint : AppCompatActivity() {

        val executor = Executors.newSingleThreadExecutor()
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        // user clicked negative button
                    } else {
                        TODO("Called when an unrecoverable error has been encountered and the operation is complete.")
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@FingerPrint, MainActivity::class.java))
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        this@FingerPrint,
                        "Remove dirt from the sensor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })


        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login to the BarcodeScanner")
            .setNegativeButtonText("Cancel")
            .build()


    fun locotest(){
        biometricPrompt.authenticate(promptInfo)
    }




    }


