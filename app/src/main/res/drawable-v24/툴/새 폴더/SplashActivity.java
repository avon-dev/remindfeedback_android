package com.example.wanna_bet70;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class    SplashActivity extends Activity {//로딩창 구현
    protected  void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        try{
            Thread.sleep(4000);//4초 쉬고
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


       // startActivity(new Intent(this,LoginActivity.class));//로그인창 오픈
       // finish();

    }



}
