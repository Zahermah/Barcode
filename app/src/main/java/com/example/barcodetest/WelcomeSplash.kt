package com.example.barcodetest

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.TextView
import kotlinx.android.synthetic.main.splash_activity.*
import org.w3c.dom.Text

class WelcomeSplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
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
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()

            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }

        })

        valueAnimator.start()
    }
}