package com.example.wanna_bet70;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class Game_Attend_Box_Activity extends AppCompatActivity {
    ArrayList<GameAttendBox> mArrayList;//임시
    RecyclerView attend_Box_Recyclerview;
    GameAttendDialogAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_attend_box_activity);


        attend_Box_Recyclerview = findViewById(R.id.attend_Box_Recyclerview);
        mArrayList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        attend_Box_Recyclerview.setLayoutManager(layoutManager);
        adapter = new GameAttendDialogAdapter(this, mArrayList);
        attend_Box_Recyclerview.setAdapter(adapter);

        adapter.addItem(new GameAttendBox("김민수"));
        adapter.addItem(new GameAttendBox("최민수"));
        adapter.addItem(new GameAttendBox("강민수"));
        adapter.addItem(new GameAttendBox("오민수"));
        adapter.addItem(new GameAttendBox("차민수"));
        adapter.addItem(new GameAttendBox("김민수"));
        adapter.addItem(new GameAttendBox("최민수"));
        adapter.addItem(new GameAttendBox("강민수"));
        adapter.addItem(new GameAttendBox("오민수"));
        adapter.addItem(new GameAttendBox("차민수"));
        adapter.addItem(new GameAttendBox("김민수"));
        adapter.addItem(new GameAttendBox("최민수"));
        adapter.addItem(new GameAttendBox("강민수"));
        adapter.addItem(new GameAttendBox("오민수"));
        adapter.addItem(new GameAttendBox("차민수"));
        adapter.addItem(new GameAttendBox("김민수"));
        adapter.addItem(new GameAttendBox("최민수"));
        adapter.addItem(new GameAttendBox("강민수"));
        adapter.addItem(new GameAttendBox("오민수"));
        adapter.addItem(new GameAttendBox("차민수"));



        Intent intent = getIntent();

       if(intent != null){
           String data = intent.getStringExtra("add_attend");

           Intent intent1 = new Intent(getApplicationContext(), GameInfoActivity.class);

           if(intent1 != null){
               intent1.putExtra("add_attned", data);
               setResult(RESULT_OK, intent1);
               //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
               //finish();
           }

       }




        //intent.putExtra("edtadd_attned", data);


    }

    protected void onPause() {

        super.onPause();



        Intent intent = getIntent();

        if(intent != null){
            String data = intent.getStringExtra("add_attend");



            Intent intent1 = new Intent(getApplicationContext(), GameInfoActivity.class);

            if(intent1 != null){
                intent1.putExtra("add_attned", data);
                setResult(RESULT_OK, intent1);
                //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                //finish();
            }

        }

    }

}
