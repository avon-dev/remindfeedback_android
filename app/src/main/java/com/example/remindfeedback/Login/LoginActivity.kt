package com.example.remindfeedback.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.remindfeedback.FeedbackList.MainActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import com.jkyeo.splashview.SplashView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity  : AppCompatActivity(), ContractLogin.View{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // 스플레시 띄우는 부분
        SplashView.showSplashView(this, 3, R.drawable.logo_1, object : SplashView.OnSplashViewActionListener {
            override fun onSplashImageClick(actionUrl: String) {
                Log.d("SplashView", "img clicked. actionUrl: $actionUrl")
                Toast.makeText(this@LoginActivity, "img clicked.", Toast.LENGTH_SHORT).show()
            }

            override fun onSplashViewDismiss(initiativeDismiss: Boolean) {
                Log.d("SplashView", "dismissed, initiativeDismiss: $initiativeDismiss")
            }
        })

        //로그인버튼
        login_Button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //finish()

        }

        //회원가입 버튼
        register_Tv.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            //finish()

        }


    }
}