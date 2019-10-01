package com.example.barcodetest.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.example.barcodetest.MainActivity
import com.example.barcodetest.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity


class LoginAuthticate : Activity() {

    var firebaseAuth = FirebaseAuth.getInstance()

    fun loginUser(view: LoginActivity) {
        val userEmail = view.user_email.text.toString().trim()
        val userPassword = view.user_password.text.toString().trim()

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(view, "Please enter your'e email", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(view, "Please enter your'e password", Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(view) { task ->
                if (task.isSuccessful) {
                    view.startActivity(Intent(view, MainActivity::class.java))
                } else {
                    Toast.makeText(view, "Wrong password or email", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signUpUser(view: LoginActivity) {
        val userEmail = view.user_email.text.toString().trim()
        val userPassword = view.user_password.text.toString().trim()

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(view, "Please enter your'e email", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(view, "Please enter your'e password", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(view) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(view, "Sucessfully User created", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(view, "Account Already exists in Database", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        fun authicateUser() {
            if (firebaseAuth.currentUser != null) {
                startActivity(Intent(applicationContext, MainActivity::class.java))

            }
        }

    }

}