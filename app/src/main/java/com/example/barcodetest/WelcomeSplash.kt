package com.example.barcodetest

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.barcodetest.view.LoginActivity
import com.google.firebase.FirebaseApp

class WelcomeSplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        FirebaseApp.initializeApp(this)
        showAnimation()
    }

    fun showAnimation() {
        val spalshTextView: TextView = findViewById(R.id.textTitle)
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            spalshTextView.scaleX = value
            spalshTextView.scaleY = value
        }

        valueAnimator.interpolator = BounceInterpolator()
        valueAnimator.duration = 1500

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()

            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}

        })

        valueAnimator.start()
    }
}