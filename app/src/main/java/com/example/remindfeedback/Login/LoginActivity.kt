package com.example.remindfeedback.Login

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.remindfeedback.Login.FindPassword.FindPasswordActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.util.Log
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.example.remindfeedback.Fab



class LoginActivity : AppCompatActivity(), ContractLogin.View {




    private val TAG = "LoginActivity"
    internal lateinit var presenterLogin: PresenterLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        setContentView(R.layout.test_login_1)



        presenterLogin = PresenterLogin().apply {
            view = this@LoginActivity
            mContext = this@LoginActivity
        }
        //권한 허용해주는부분
        presenterLogin.getPermission()

        //로그인버튼
        login_Button.setOnClickListener {
            if (email_Edittext.text.isNotEmpty() && password_Edittext.text.isNotEmpty()) {
                presenterLogin.LogIn(email_Edittext.text.toString(), password_Edittext.text.toString())
                email_Edittext.setText("")
                password_Edittext.setText("")

                // finish()
            } else if (email_Edittext.text.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (password_Edittext.text.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        forgot_Password.setOnClickListener{
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }

        //회원가입 버튼
        register_Tv.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            //finish()
        }





    }



}