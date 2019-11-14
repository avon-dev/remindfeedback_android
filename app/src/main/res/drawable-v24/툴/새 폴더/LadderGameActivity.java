package com.example.wanna_bet70;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LadderGameActivity extends AppCompatActivity {


    private static MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder_game);

        TextView game_Script=(TextView)findViewById(R.id.game_Script);
        TextView game_Reward=(TextView)findViewById(R.id.game_Reward);
        Bundle extras = getIntent().getExtras();
        String s = extras.getString("game_Script");
        game_Script.setText(s);

        String str = extras.getString("game_Reward");
        game_Reward.setText(str);

        mp = MediaPlayer.create(this, R.raw.bgm);
        mp.setLooping(true);//무한루프
        mp.start();//음악 재생
        Log.w(this.getClass().getName(), "onCreate실행");



        Button cut = (Button) findViewById(R.id.cut);//마이페이지 버튼누르면 다음화면으로

        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });





    }



protected void onStart() {

    super.onStart();
    Log.w(this.getClass().getName(), "onStart실행");

}

    protected void onResume() {

        super.onResume();

        Log.w(this.getClass().getName(), "onResume실행");


    }

    protected void onPause() {
        super.onPause();
        mp.pause();
        Log.w(this.getClass().getName(), "onPause실행");
    }

    protected  void onStop() {

        super.onStop();
        Log.w(this.getClass().getName(), "onStop실행");


    }

    protected void onRestart() {

        super.onRestart();



        AlertDialog.Builder builder = new AlertDialog.Builder(LadderGameActivity.this);
        builder.setMessage("게임이 일시 정지 되었습니다.\n계속하시겠습니까?")

                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mp.start();
                        dialog.cancel();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.cancel();// 노 누르면 다이얼로그가 꺼짐
                        finish();
                    }
                });



        AlertDialog alert = builder.create();
        alert.setTitle("일시정지");
        alert.show();

    }

}
