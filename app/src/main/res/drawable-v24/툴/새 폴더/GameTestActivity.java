package com.example.wanna_bet70;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class GameTestActivity extends AppCompatActivity {

    boolean p1check;
    boolean p2check;


    Chronometer mChrono;//크로노 미터 선언


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);

        mChrono = (Chronometer)findViewById(R.id.chrono);

        final ImageView p1bomb = (ImageView) findViewById(R.id.p1bomb);
        final ImageView p2bomb = (ImageView) findViewById(R.id.p2bomb);
        p1bomb.setVisibility(View.INVISIBLE);
        p2bomb.setVisibility(View.INVISIBLE);
        p1check = false;
        p2check = false;

        Random random = new Random();
        int i = random.nextInt(2)+1;
        if(i == 1){
            p1bomb.setVisibility(View.VISIBLE);
            p1check = true;
        }
        else{
            p2bomb.setVisibility(View.VISIBLE);
            p2check = true;
        }


        ImageButton bomb_button01 = (ImageButton) findViewById(R.id.bomb_button01);
        bomb_button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(p1check){
                    p1check = false;
                    p2check = true;
                    p1bomb.setVisibility(View.INVISIBLE);
                    p2bomb.setVisibility(View.VISIBLE);
                }
                else{

                }


            }
        });


        ImageButton bomb_button02 = (ImageButton) findViewById(R.id.bomb_button02);

        bomb_button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(p2check){
                    p2check = false;
                    p1check = true;
                    p2bomb.setVisibility(View.INVISIBLE);
                    p1bomb.setVisibility(View.VISIBLE);
                }
                else{

                }



            }
        });


    }

    protected void onResume() {

        super.onResume();


        //카운트 다운 타이머
        CountDownTimer mCountDown  = new CountDownTimer(10000, 1000){//카운트다운 변수에 제한시간동안 시간간격, 타이머 설정

            @Override
            public void onTick(long millisUntilFinished) {//타이머가 종료될때까지 동작하는 함수
                mChrono.start();
            }

            @Override
            public void onFinish() {//타이머가 종료될때 동작하는 함수

                if(p1check){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameTestActivity.this);
                    builder.setTitle("결과")
                            .setMessage("player 1 님이 승리하셨습니다.")

                            .setCancelable(false)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                  finish();


                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else if(p2check){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameTestActivity.this);
                    builder.setTitle("결과")
                            .setMessage("player 2 님이 승리하셨습니다.")

                            .setCancelable(false)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();


                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"오류",Toast.LENGTH_SHORT).show();
                }


                mChrono.stop();
            }
        }.start();


    }



    public void onDestroy(){//크로노미터 종료, 메모리릭 방지

        super.onDestroy();

        mChrono.stop();

    }








}
