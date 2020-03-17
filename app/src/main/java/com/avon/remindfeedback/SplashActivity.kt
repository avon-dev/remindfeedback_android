package com.avon.remindfeedback

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.avon.remindfeedback.Login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var intent= Intent(this, LoginActivity::class.java)
        val animationView = findViewById<LottieAnimationView>(R.id.animationView)

        animationView.setAnimation("2233-downloading-book.json")
        animationView.loop(false)
        animationView.playAnimation()
        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.e("asd", "onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator) {
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })


    }
}
