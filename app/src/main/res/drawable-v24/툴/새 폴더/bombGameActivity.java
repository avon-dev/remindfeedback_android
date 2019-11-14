package com.example.wanna_bet70;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class bombGameActivity extends AppCompatActivity {
    private Button mStartBtn, mStopBtn, mRecordBtn, mPauseBtn;
    private TextView mTimeTextView, mRecordTextView;
    private Thread timeThread = null;
    private Boolean isRunning = true;
    private  Boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bomb_game);



    }
    protected void onResume() {
        super.onResume();
        if(check){
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(Color.parseColor("#4ea1d3"));
            }


            mTimeTextView = (TextView) findViewById(R.id.timeView);

            timeThread = new Thread(new timeThread());
            timeThread.start();
        }
        else{
            isRunning = !isRunning;

        }

    }
    protected void onPause() {
        super.onPause();
        check = false;
        isRunning = !isRunning;

    }






    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec);
            if (result.equals("00:00:10:00")) {
                Toast.makeText(bombGameActivity.this, "1분이 지났습니다.\n3초후 게임이 종료 됩니다.", Toast.LENGTH_SHORT).show();
            }
            if (result.equals("00:00:13:00")) {
                finish();
            }

            mTimeTextView.setText(result);
        }
    };

    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;


            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                mTimeTextView.setText("");
                                mTimeTextView.setText("00:00:00:00");

                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }



}
