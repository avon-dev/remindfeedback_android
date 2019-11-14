package com.example.wanna_bet70;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BetListActivity extends AppCompatActivity {
    ArrayList<Bet> mArrayList;//임시
    RecyclerView bet_recyclerview;
    BetAdapter adapter;
    long now = System.currentTimeMillis();//시간 받아오는 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_list);

        bet_recyclerview = findViewById(R.id.bet_recyclerview);
        mArrayList = new ArrayList<>();//임시

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        bet_recyclerview.setLayoutManager(layoutManager);


        adapter = new BetAdapter(this, mArrayList);



        bet_recyclerview.setAdapter(adapter);
//여기부터 임시 버튼클릭
        Button bet_list_plus = (Button)findViewById(R.id.bet_list_plus);
        bet_list_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BetListActivity.this);
                View view = LayoutInflater.from(BetListActivity.this)
                        .inflate(R.layout.bet_edit_box, null, false);
                builder.setView(view);

                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                final EditText editTextPeople = (EditText) view.findViewById(R.id.edittext_bet_people);
                final EditText editTextScript = (EditText) view.findViewById(R.id.edittext_bet_script);
                final EditText editTextWin = (EditText) view.findViewById(R.id.edittext_bet_win);
                final EditText editTextReward = (EditText) view.findViewById(R.id.edittext_bet_reward);

                ButtonSubmit.setText("삽입");


                final AlertDialog dialog = builder.create();
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {


                        //날짜는 등록하는 시간의 것을 가져와서 등록함
                        Date date = new Date(now);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
                        String strDate = sdf.format(date);
                        String strPeople = editTextPeople.getText().toString();
                        String strScript = editTextScript.getText().toString();
                        String strWin = editTextWin.getText().toString();
                        String strReward = editTextReward.getText().toString();

                        Bet dict = new Bet( strDate, strPeople, strScript,strWin,strReward  );

                        //mArrayList.add(0, dict); //첫 줄에 삽입
                        mArrayList.add(dict); //마지막 줄에 삽입
                        adapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

                        dialog.dismiss();
                        return;




                    }
                });

                dialog.show();
            }
        });
//여기까지 임시 버튼클릭

        adapter.addItem(new Bet( "2019년 03월 15일","강xx", "짜장면내기", "승", "짜장면값"));
        adapter.addItem(new Bet( "2019년 03월 18일","최xx", "5만원내기", "승", "5만원"));
        adapter.addItem(new Bet("2019년 03월 20일","고xx", "진사람 소주원샷", "패", "소주원샷"));
        adapter.addItem(new Bet( "2019년 03월 22일","이xx", "짜장면내기", "승", "짜장면값"));
        adapter.addItem(new Bet( "2019년 03월 19일","강xx", "짜장면내기", "승", "짜장면값"));




    }
}
