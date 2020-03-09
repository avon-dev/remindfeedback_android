package com.example.remindfeedback.Register

import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.remindfeedback.DocumentActivity
import com.example.remindfeedback.R
import com.example.remindfeedback.etcProcess.Sha256Util
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity(), ContractRegister.View {

    var isVerified : Boolean = false

    private val TAG = "RegisterActivity"
    internal lateinit var presenterRegister: PresenterRegister


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Log.e("Sha256Util", Sha256Util.testSHA256("1"))

        //액션바 설정
        var ab: ActionBar = this.supportActionBar!!
        ab.setTitle("회원가입")
        //뒤로가기 버튼 만들어주는부분 -> 메니페스트에 부모액티비티 지정해줘서 누르면 그쪽으로 가게끔함
        ab.setDisplayHomeAsUpEnabled(true)

        presenterRegister = PresenterRegister().apply {
            view = this@RegisterActivity
            mContext = this@RegisterActivity
        }


        send_Token_Button.setOnClickListener{
            presenterRegister.verify(email_Input.text.toString())

        }


        //회원가입버튼을 눌러서 presenter의 회원가입 기능을 실행시킴
        register_Button.setOnClickListener {
            if(isVerified){
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
                                //토큰입력했는지?
                                if(token_Input.text.toString().equals("")){
                                    Toast.makeText(this, "이메일로 전송된 토큰을 입력해주세요.", Toast.LENGTH_SHORT).show()

                                }else{
                                    // 이용약관 동의 확인
                                    if ( !chk_1.isChecked || !chk_2.isChecked) {
                                        Toast.makeText(this, "이용약관에 모두 동의하여 주세요.", Toast.LENGTH_SHORT).show()
                                    } else {

                                        presenterRegister.signup(email_Input.text.toString(), nickname_Input.text.toString(),Sha256Util.testSHA256(password_Input.text.toString()), token_Input.text.toString())
                                        //finish()
                                    }

                                }


                            }
                        }

                    }
                }
            }else{
                Toast.makeText(this, "이메일 인증을 먼저 해주세요.", Toast.LENGTH_SHORT).show()
            }

        }

        chk_all.setOnClickListener{
            if(chk_all.isChecked){
                chk_1.isChecked = true
                chk_2.isChecked = true
            }else if(!chk_all.isChecked){
                chk_1.isChecked = false
                chk_2.isChecked = false
            }

        }

        chk_1.setOnClickListener{
            if(chk_1.isChecked){
                chk_1.isChecked = true
            }else{
                chk_1.isChecked = false
            }
            if(chk_1.isChecked &&chk_2.isChecked){
                chk_all.isChecked = true
            }else{
                chk_all.isChecked = false
            }
        }
        chk_2.setOnClickListener{
            if(chk_2.isChecked){
                chk_2.isChecked = true
            }else{
                chk_2.isChecked = false
            }
            if(chk_1.isChecked &&chk_2.isChecked ){
                chk_all.isChecked = true
            }else{
                chk_all.isChecked = false
            }
        }



        rf_Use_Info.setOnClickListener{
            val intent = Intent(this, DocumentActivity::class.java)
            intent.putExtra("script", getString(R.string.rfUsingInfo))
            intent.putExtra("type", "rfUsingInfo")
            startActivityForResult(intent, 111)
        }
        privacy_Info.setOnClickListener{
            val intent = Intent(this, DocumentActivity::class.java)
            intent.putExtra("script",getString(R.string.privacyInfo))
            intent.putExtra("type", "privacyInfo")
            startActivityForResult(intent, 111)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            111 -> {
                if(data!!.getStringExtra("type").equals("rfUsingInfo")){
                    if(!chk_1.isChecked){
                        chk_1.isChecked = true
                    }
                    if(chk_1.isChecked &&chk_2.isChecked){
                        chk_all.isChecked = true
                    }
                }else if(data!!.getStringExtra("type").equals("privacyInfo")){
                    if(!chk_2.isChecked){
                        chk_2.isChecked = true
                    }
                    if(chk_1.isChecked &&chk_2.isChecked ){
                        chk_all.isChecked = true
                    }
                }
            }

        }
    }

    override fun tokenSended(){
        send_Token_Button.isEnabled = false
        isVerified = true
    }
}
