package com.example.barcodetest.animation

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.BounceInterpolator
import com.example.barcodetest.view.LoginActivity
import kotlinx.android.synthetic.main.login_activity.*

class showTextAnimation {

    fun showAnimation(view: LoginActivity) {

        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            view.barcode_slide.scaleX = value
            view.barcode_slide.scaleY = value
        }
        valueAnimator.interpolator = BounceInterpolator()
        valueAnimator.duration = 1000

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}

        })

        valueAnimator.start()
    }


}