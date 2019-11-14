package com.example.wanna_bet70;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    protected  void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        try{
            Thread.sleep(4000);//4초 쉬고
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this,LoginActivity.class));//로그인창 오픈



    }

    protected  void onResume() {

        super.onResume();

    }



    protected void onStop() {

        super.onStop();
        finish();
    }
}
