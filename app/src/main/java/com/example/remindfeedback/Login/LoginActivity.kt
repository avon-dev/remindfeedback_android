package com.example.remindfeedback.Login

import android.animation.Animator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.Register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.remindfeedback.Login.FindPassword.FindPasswordActivity
import com.rey.material.app.BottomSheetDialog
import com.rey.material.drawable.ThemeDrawable
import com.rey.material.util.ViewUtil


class LoginActivity : AppCompatActivity(), ContractLogin.View {



    var mBottomSheetDialog: BottomSheetDialog? = null

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
            showBottomSheet()
        }

        //회원가입 버튼
        register_Tv.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            //finish()
        }





    }


    private fun showBottomSheet() {
        mBottomSheetDialog = BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog)
        val v = LayoutInflater.from(this).inflate(R.layout.view_find_password_bottomsheet, null)
        ViewUtil.setBackground(v, ThemeDrawable(R.drawable.bg_window_light))
        val sheet_Find_Password = v.findViewById<View>(R.id.sheet_Find_Password) as Button
        val sheet_Find_Email = v.findViewById<View>(R.id.sheet_Find_Email) as Button
        val sheet_Find_Cancel = v.findViewById<View>(R.id.sheet_Find_Cancel) as Button

        sheet_Find_Password.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            intent.putExtra("state", "password")
            startActivity(intent)
            mBottomSheetDialog!!.dismissImmediately()
        }
        sheet_Find_Email.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            intent.putExtra("state", "email")
            startActivity(intent)
            mBottomSheetDialog!!.dismissImmediately()
        }

        sheet_Find_Cancel.setOnClickListener { mBottomSheetDialog!!.dismissImmediately() }
        v.setBackgroundColor(Color.parseColor("#ffffff"))
        //v.setBackgroundResource(R.drawable.all_line)
        sheet_Find_Password.setBackgroundColor(Color.parseColor("#ddeeff"))
        sheet_Find_Email.setBackgroundColor(Color.parseColor("#ddeeff"))
        sheet_Find_Cancel.setBackgroundColor(Color.parseColor("#ddeeff"))

        mBottomSheetDialog!!.contentView(v)
            .show()
    }

    override fun onPause() {
        super.onPause()
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog!!.dismissImmediately()
            mBottomSheetDialog = null
        }
    }





}