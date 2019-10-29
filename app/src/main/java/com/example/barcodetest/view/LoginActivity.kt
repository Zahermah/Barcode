package com.example.barcodetest.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.example.barcodetest.MainActivity
import com.example.barcodetest.R
import com.example.barcodetest.animation.showTextAnimation
import com.example.barcodetest.network.AppNetworkStatus
import com.example.barcodetest.presenter.LoginAuthticate
import com.google.firebase.FirebaseApp
import com.rbddevs.splashy.Splashy

import kotlinx.android.synthetic.main.login_activity.*
import java.util.concurrent.Executors


open class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.qualifiedName
    lateinit var view: LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSplashy()
        setContentView(R.layout.login_activity)
        FirebaseApp.initializeApp(this)
        showTextAnimation().showAnimation(this)
        checkNetworkStatus()

        LoginUser_button.setOnClickListener {
            LoginAuthticate().loginUser(this)
        }
        SignUp_Button.setOnClickListener {
            LoginAuthticate().signUpUser(this)
        }

        Finger_Print.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }

    fun setSplashy() {
        Splashy(this)         // For JAVA : new Splashy(this)
            .setLogo(R.drawable.barcodereader)
            .setTitle(R.string.app_name)
            .setTitleColor(R.color.color_amber_dark_400)
            .setSubTitle("For your Scanning purpose")
            .setProgressColor(R.color.white)
            .setBackgroundResource(R.color.color_teal_dark_700)
            .setAnimation(Splashy.Animation.SLIDE_LEFT_ENTER)
            .setFullScreen(true)
            .setTime(5000)
            .show()
    }

    fun checkNetworkStatus() {
        AppNetworkStatus { internet ->
            Toast.makeText(
                this,
                "Is connection enabled? " + internet,
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    val executor = Executors.newSingleThreadExecutor()
    val biometricPrompt =
        BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    //Snackbar.make(R.layout.login_activity,"Error place your finger on the sensor",Snackbar.LENGTH_SHORT)
                    if (applicationContext != null) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error place your finger on the sensor",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(
                    applicationContext,
                    "Remove dirt from the sensor",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Login to the BarcodeScanner")
        .setNegativeButtonText("Cancel")
        .build()


}
