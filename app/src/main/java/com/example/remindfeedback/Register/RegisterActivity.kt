package com.example.remindfeedback.Register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern
import android.util.Log
import com.example.remindfeedback.R


class RegisterActivity : AppCompatActivity(), ContractRegister.View {
    private var mEncryptText: ByteArray? = null
    private var mKey: ByteArray? = null

    private val TAG = "RegisterActivity"
    internal lateinit var presenterRegister: PresenterRegister


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //액션바 설정
        var ab: ActionBar = this.supportActionBar!!
        ab.setTitle("회원가입")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        presenterRegister = PresenterRegister().apply {
            view = this@RegisterActivity
            mContext = this@RegisterActivity
        }

        //회원가입버튼을 눌러서 presenter의 회원가입 기능을 실행시킴
        register_Button.setOnClickListener {

            if (email_Input.text.toString().equals("") || nickname_Input.text.toString().equals("") || password_Input.text.toString().equals("") || re_Password_Input.text.toString().equals("")) {
                Toast.makeText(this@RegisterActivity, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show()
            } else {

                // 이메일 형식 체크
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email_Input.text).matches()) {
                    Toast.makeText(this, "이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
                } else {

                    // 비밀번호 & 비밀번호 확인 일치하는지 체크
                    if( !password_Input.text.toString().equals(re_Password_Input.text.toString()) ) {
                        Toast.makeText(this@RegisterActivity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    } else {

                        // 비밀번호 형식 체크
                        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$", password_Input.text.toString())) {
                            Toast.makeText(this, "비밀번호 형식을 지켜주세요.\n(영문,숫자,특수문자 포함 최소 8글자)", Toast.LENGTH_SHORT).show()
                        } else {

                            // 이용약관 동의 확인
                            if ( !chk_1.isChecked || !chk_2.isChecked || !chk_3.isChecked ) {
                                Toast.makeText(this, "이용약관에 모두 동의하여 주세요.", Toast.LENGTH_SHORT).show()
                            } else {
                                presenterRegister.signup(email_Input.text.toString(), nickname_Input.text.toString(), password_Input.text.toString())
                                //finish()
                            }
                        }
                    }

                }
            }

        }

    }
}
