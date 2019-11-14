package com.example.wanna_bet70;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    Button register_ok_Button;
    EditText pw;
    EditText pw_Re;
    EditText email;
    EditText name;
    EditText phone;


    JSONObject jsonObject;
    JSONArray jsonArray = new JSONArray();
    JSONObject mainObject = new JSONObject();
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Context context = this;


        try {
            mainObject.put("account", jsonArray);

        } catch (JSONException e1) {

            // TODO Auto-generated catch block

            e1.printStackTrace();

        }



        json = null;

        try{//제이슨 파일 불러오는 부분
            InputStream is = getAssets().open("accountInfo.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }//String 형식으로 데이터 불러와졌음



//회원가입에서 아이디에 입력한게 idText변수에 입력됨
       pw = (EditText) findViewById(R.id.pwText);
        pw_Re = (EditText) findViewById(R.id.pwText_Re);
       email = (EditText) findViewById(R.id.emailText);
        name = (EditText) findViewById(R.id.nameText);
       phone = (EditText) findViewById(R.id.phoneText);


        register_ok_Button = (Button) findViewById(R.id.register_ok_Button);//마이페이지 버튼누르면 다음화면으로

        register_ok_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //임시 부분
                SharedPreferences preferences  = getSharedPreferences("USERSIGN", MODE_PRIVATE);
                String pwText = pw.getText().toString();
                String pwText_Re = pw_Re.getText().toString();
                String emailText = email.getText().toString();
                String nameText = name.getText().toString();
                String phoneText = phone.getText().toString();

                String bPass = preferences.getString(emailText, "");



                boolean check1 = false;
                boolean check2 = false;
                boolean check3 = false;
                boolean check4 = false;

                if(pwText.equals("") || emailText.equals("") ||nameText.equals("") || phoneText.equals("")  ){
                    Toast.makeText(RegisterActivity.this, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                }
                else if (!pwText.equals(pwText_Re)) {
                    Toast.makeText(context, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }
                else{
                    check1 = true;
                    String aaa = email.getText().toString();

                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(aaa).matches()){
                        check2 = true;
                    }else {
                        Toast.makeText(RegisterActivity.this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String ccc = pw.getText().toString();
                    if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", ccc))
                    {
                        Toast.makeText(RegisterActivity.this,"비밀번호 형식을 지켜주세요.",Toast.LENGTH_SHORT).show();
                        return;

                    }
                    else{
                        check4 = true;
                    }

                    String bbb = phone.getText().toString();

                    if(bbb.length() != 11 )
                    {
                        Toast.makeText(RegisterActivity.this,"올바른 핸드폰 번호가 아닙니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        check3 = true;
                    }





                }
                if(check1 == true && check2 == true && check3 == true && check4 == true){

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("회원가입 하시겠습니까?")

                            .setCancelable(false)
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    String emailText = email.getText().toString();
                                    String nameText = name.getText().toString();
                                    String phoneText = phone.getText().toString();
                                    String pwText = pw.getText().toString();
                                    String pwText_Re = pw_Re.getText().toString();





                                    if (!Utils.valid(emailText))
                                        Toast.makeText(context, "이메일이 잘못되었습니다.", Toast.LENGTH_LONG).show();
                                    else if (pwText.length() < 4)
                                        Toast.makeText(context, "비밀번호가 짧습니다.", Toast.LENGTH_LONG).show();
                                   else if (!pwText.equals(pwText_Re)) {
                                        Toast.makeText(context, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                                    } else {

                                       Log.e("ddd", "엘스문 오케이");



                                        try{
                                            Log.e("ddd", "엘스문 오케이3");
                                            jsonObject = new JSONObject(json);
                                            JSONArray jsonArray = jsonObject.getJSONArray("accountInfo");

                                            Gson gson = new Gson();



                                            jsonObject.put("email", emailText);
                                            jsonObject.put("passward", pwText);
                                            jsonObject.put("phone", phoneText);
                                            jsonObject.put("name", nameText);

                                            jsonArray.put(jsonObject);


                                        }catch (JSONException e){
                                            Log.e("ddd", "엘스문 오케이2");
                                        }



                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        RegisterActivity.this.startActivity(intent);
                                        finish();

                                    }


                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    dialog.cancel();// 노 누르면 다이얼로그가 꺼짐
                                }
                            });



                    AlertDialog alert = builder.create();
                    alert.setTitle("알림");
                    alert.show();



                }


            }
        });



    }


}
