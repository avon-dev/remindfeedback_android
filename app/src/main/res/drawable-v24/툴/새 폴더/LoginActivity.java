package com.example.wanna_bet70;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    //SharedPreferences preferences;
    Button Login_Button;
    TextView Register_Button;
    CheckBox auto_Login;
    private long time= 0;
    JSONObject obj;
    String json;
    ArrayList<Account> account_Info = new ArrayList<>();

    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Context context = this;
/*
        try{

            obj = new JSONObject(accountInfo.toString());//jobj2를 문자열로 바꾸어서  새로운 오브젝트로 만들어 저장

        }catch (JSONException e){
            e.printStackTrace();
        }
*/

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
        //Toast.makeText(context, json, Toast.LENGTH_SHORT).show();




        final EditText id_String_Text = (EditText) findViewById(R.id.id_String_Text);
        final EditText pw_String_Text = (EditText) findViewById(R.id.pw_String_Text);
        Login_Button = (Button) findViewById(R.id.Login_Button);//마이페이지 버튼누르면 다음화면으로
        Register_Button = (TextView) findViewById(R.id.Register_Button);//마이페이지 버튼누르면 다음화면으로
        auto_Login = (CheckBox)findViewById(R.id.auto_Login);
/*
        preferences  = getSharedPreferences("USERSIGN", MODE_PRIVATE);

        final boolean uLogin = preferences.getBoolean("login", false);//여기 자동로그인 인듯
        if (uLogin){//자동로그인임!
            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
            LoginActivity.this.startActivity(intent);
        }
*/

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = id_String_Text.getText().toString();
                String pw = pw_String_Text.getText().toString();
               // String bPass = preferences.getString(email, "-");//email 값과 일치하는 패스워드값? 받아오는듯?



                if (!email.contains("@") || email.length() < 6)
                    Toast.makeText(context, "이메일이 정확하지 않습니다.", Toast.LENGTH_LONG).show();
                else if (pw.length() < 4)
                    Toast.makeText(context, "비밀번호의 길이가 짧습니다.", Toast.LENGTH_LONG).show();
               // else if (!Utils.md5Custom(pw).equals(bPass))
               //     Toast.makeText(context,"비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                else {
                    /*
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user", email);
                    if(auto_Login.isChecked() == true){
                        editor.putBoolean("login", true);
                    }
                    editor.commit();
*/





                    try{
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray jsonArray = jsonObject.getJSONArray("accountInfo");
                        int index = 0;
                        while (index < jsonArray.length()) {
                            Account account = gson.fromJson(jsonArray.get(index).toString(), Account.class);
                            account_Info.add(account);

                            String id = account.getEmail();
                            String p_w = account.getPassward();
                            if(id.equals(email)&&p_w.equals(pw)){
                                Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                            }

                            index++;
                        }
                    }catch (JSONException e){

                    }



                }



            }
        });




        Register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView textView3 = (TextView) findViewById(R.id.textView3);//마이페이지 버튼누르면 다음화면으로

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(System.currentTimeMillis()-time>=2000){
                    time=System.currentTimeMillis();
                    Toast.makeText(getApplicationContext(),"한번 더누르면 로그인합니다.",Toast.LENGTH_SHORT).show();
                }else if(System.currentTimeMillis()-time<2000){

                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }




            }
        });



    }


    private ArrayList<Account> getAlbumList() {

        ArrayList<Account> account_Info = new ArrayList<>();

        Gson gson = new Gson();



        try {

            InputStream is = getAssets().open("accountInfo.json");

            byte[] buffer = new byte[is.available()];

            is.read(buffer);

            is.close();

            String json = new String(buffer, "UTF-8");



            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("accountInfo");



            int index = 0;

            while (index < jsonArray.length()) {

                Account account = gson.fromJson(jsonArray.get(index).toString(), Account.class);

                account_Info.add(account);



                index++;

            }



        } catch (Exception e) {

            e.printStackTrace();

        }



        return account_Info;

    }


}
