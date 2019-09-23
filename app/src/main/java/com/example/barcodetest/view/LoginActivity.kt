package com.example.barcodetest.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.barcodetest.MainActivity
import com.example.barcodetest.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*


class LoginActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        LoginUser_button.setOnClickListener {
            loginUser()
        }
        SignUp_Button.setOnClickListener {
            signUpUser()
        }
    }

    fun loginUser() {
        var userEmail = user_email.text.toString().trim()
        var userPassword = user_password.text.toString().trim()


        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Please enter your'e email", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Please enter your'e password", Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    finish()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Wrong password or email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun signUpUser() {
        val userEmail = user_email.text.toString().trim()
        val userPassword = user_password.text.toString().trim()


        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Please enter your'e email", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Please enter your'e password", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sucessfully User created", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Account Already exists in Database", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        fun authicateUser() {
            if (firebaseAuth.currentUser != null) {
                finish()
                startActivity(Intent(applicationContext, MainActivity::class.java))

            }
        }

    }
}
