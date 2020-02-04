package com.example.remindfeedback.Login.FindPassword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.remindfeedback.Login.PresenterLogin
import com.example.remindfeedback.R

import kotlinx.android.synthetic.main.activity_find_password.*

class FindPasswordActivity : AppCompatActivity(), ContractFindPassword.View {

    lateinit var presenterFindPassword: PresenterFindPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)
        presenterFindPassword = PresenterFindPassword().apply {
            view = this@FindPasswordActivity
            context = this@FindPasswordActivity
        }

        find_Password_Token_Layout.visibility = View.GONE

        find_Password_Email_Button.setOnClickListener{
            if(!find_Password_Email_Tv.text.toString().equals("")){
                presenterFindPassword.findPassword(find_Password_Email_Tv.text.toString())
                find_Password_Token_Layout.visibility = View.VISIBLE
                find_Password_Email_Layout.visibility = View.GONE
                find_Password_Main_Tv.text = "입력된 이메일로 보내진 토큰을 확인하고 새 비밀번호와 함께 입력해주세요."
            }else{
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
            }
        }

        find_Password_Token_Button.setOnClickListener{
            if(find_Password_Token_Tv.text.toString().equals("")){
                Toast.makeText(this, "토큰을 입력해주세요", Toast.LENGTH_LONG).show()
            }else if(find_Password_Password_Tv.text.toString().equals("")){
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            }else if(!find_Password_Password_Tv.text.toString().equals(find_Password_Confirm_Password_Tv.text.toString())){
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_LONG).show()
            }else{
                presenterFindPassword.changingPassword(find_Password_Token_Tv.text.toString(), find_Password_Password_Tv.text.toString())
            }
        }
    }

}
