package com.example.wanna_bet70;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CrocoActivity extends AppCompatActivity {

    int i;

    //이빨들중에 무엇이 당첨이빨인지?
    Boolean teeth1bool = true;
    Boolean teeth2bool = true;
    Boolean teeth3bool = true;
    Boolean teeth4bool = true;
    Boolean teeth5bool = true;
    Boolean teeth6bool = true;

    //눌린이빨인지?
    Boolean teeth1after = false;
    Boolean teeth2after = false;
    Boolean teeth3after = false;
    Boolean teeth4after = false;
    Boolean teeth5after = false;
    Boolean teeth6after = false;


    Boolean turn = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croco);


        final ImageView teeth1 = (ImageView)findViewById(R.id.teeth1);
        final ImageView teeth2 = (ImageView)findViewById(R.id.teeth2);
        final ImageView teeth3 = (ImageView)findViewById(R.id.teeth3);
        final ImageView teeth4 = (ImageView)findViewById(R.id.teeth4);
        final ImageView teeth5 = (ImageView)findViewById(R.id.teeth5);
        final ImageView teeth6 = (ImageView)findViewById(R.id.teeth6);


        final Random rand = new Random();

        i = rand.nextInt(6)+1;

        if(i == 1){
            teeth1bool = false;
        }
        else if(i == 2){
            teeth2bool = false;
        }
        else if(i == 3){
            teeth3bool = false;
        }
        else if(i == 4){
            teeth4bool = false;
        }
        else if(i == 5){
            teeth5bool = false;
        }
        else if(i == 6){
            teeth6bool = false;
        }


        teeth1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(teeth1after){
                    Toast.makeText(CrocoActivity.this, "이미 눌린 이빨 입니다.", Toast.LENGTH_SHORT).show();
                }
               else{
                   if(turn){

                       teeth1after = true;//이빨이 눌렸음
                       teeth1.setImageResource(R.drawable.teeth_after);


                       if(teeth1bool){
                           Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(getApplicationContext(),"너 탈락",Toast.LENGTH_SHORT).show();
                           finish();
                           return;
                       }

                       choise();//컴퓨터가 선택하는 함수

                   }
                   else{
                       Toast.makeText(getApplicationContext(),"당신의 차례가 아닙니다.",Toast.LENGTH_SHORT).show();
                   }
               }




            }
        });



        teeth2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teeth2after){
                    Toast.makeText(CrocoActivity.this, "이미 눌린 이빨 입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(turn){

                        teeth2after = true;//이빨이 눌렸음
                        teeth2.setImageResource(R.drawable.teeth_after);

                        if(teeth2bool){
                            Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"너 탈락",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }


                        choise();



                    }
                    else{
                        Toast.makeText(getApplicationContext(),"당신의 차례가 아닙니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        teeth3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(teeth3after){
                   Toast.makeText(CrocoActivity.this, "이미 눌린 이빨 입니다.", Toast.LENGTH_SHORT).show();
               }else{
                   if(turn){

                       teeth3after = true;//이빨이 눌렸음
                       teeth3.setImageResource(R.drawable.teeth_after);

                       if(teeth3bool){
                           Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(getApplicationContext(),"너 탈락",Toast.LENGTH_SHORT).show();
                           finish();
                           return;
                       }


                       choise();



                   }
                   else{
                       Toast.makeText(getApplicationContext(),"당신의 차례가 아닙니다.",Toast.LENGTH_SHORT).show();
                   }

               }



            }
        });



        teeth4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(teeth4after){
                   Toast.makeText(CrocoActivity.this, "이미 눌린 이빨 입니다.", Toast.LENGTH_SHORT).show();
               }else{
                   if(turn){

                       teeth4after = true;//이빨이 눌렸음
                       teeth4.setImageResource(R.drawable.teeth_after);

                       if(teeth4bool){
                           Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(getApplicationContext(),"너 탈락",Toast.LENGTH_SHORT).show();
                           finish();
                           return;
                       }


                       choise();


                   }
                   else{
                       Toast.makeText(getApplicationContext(),"당신의 차례가 아닙니다.",Toast.LENGTH_SHORT).show();
                   }
               }




            }
        });



        teeth5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(teeth5after){
                   Toast.makeText(CrocoActivity.this, "이미 눌린 이빨 입니다.", Toast.LENGTH_SHORT).show();
               }else{
                   if(turn){

                       teeth5after = true;//이빨이 눌렸음
                       teeth5.setImageResource(R.drawable.teeth_after);

                       if(teeth5bool){
                           Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(getApplicationContext(),"너 탈락",Toast.LENGTH_SHORT).show();
                           finish();
                           return;
                       }



                       choise();


                   }
                   else{
                       Toast.makeText(getApplicationContext(),"당신의 차례가 아닙니다.",Toast.LENGTH_SHORT).show();
                   }
               }




            }
        });



        teeth6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(teeth6after){
                   Toast.makeText(CrocoActivity.this, "이미 눌린 이빨 입니다.", Toast.LENGTH_SHORT).show();
               }else{
                   if(turn){

                       teeth6after = true;//이빨이 눌렸음
                       teeth6.setImageResource(R.drawable.teeth_after);

                       if(teeth6bool){
                           Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(getApplicationContext(),"너 탈락",Toast.LENGTH_SHORT).show();
                           finish();
                           return;
                       }


                       choise();


                   }
                   else{
                       Toast.makeText(getApplicationContext(),"당신의 차례가 아닙니다.",Toast.LENGTH_SHORT).show();
                   }
               }




            }
        });







    }


    public void choise(){
        Random rand = new Random();

        final ImageView teeth1 = (ImageView)findViewById(R.id.teeth1);
        final ImageView teeth2 = (ImageView)findViewById(R.id.teeth2);
        final ImageView teeth3 = (ImageView)findViewById(R.id.teeth3);
        final ImageView teeth4 = (ImageView)findViewById(R.id.teeth4);
        final ImageView teeth5 = (ImageView)findViewById(R.id.teeth5);
        final ImageView teeth6 = (ImageView)findViewById(R.id.teeth6);



        turn = false;
        for(;;){
            int i = rand.nextInt(6)+1;

            if(i == 1 && teeth1after ==false){
                Toast.makeText(getApplicationContext(),"상대방의 차례입니다.",Toast.LENGTH_SHORT).show();


                teeth1after = true;
                teeth1.setImageResource(R.drawable.teeth_after);


                if(teeth1bool){
                    Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"상대방 탈락",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;

            }
            else if(i == 2 && teeth2after == false){
                Toast.makeText(getApplicationContext(),"상대방의 차례입니다.",Toast.LENGTH_SHORT).show();


                teeth2after = true;
                teeth2.setImageResource(R.drawable.teeth_after);


                if(teeth2bool){
                    Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"상대방 탈락",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;
            }
            else if(i == 3 && teeth3after == false){
                Toast.makeText(getApplicationContext(),"상대방의 차례입니다.",Toast.LENGTH_SHORT).show();


                teeth3after = true;
                teeth3.setImageResource(R.drawable.teeth_after);


                if(teeth3bool){
                    Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"상대방 탈락",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;
            }
            else if(i == 4 && teeth4after == false){
                Toast.makeText(getApplicationContext(),"상대방의 차례입니다.",Toast.LENGTH_SHORT).show();


                teeth4after = true;
                teeth4.setImageResource(R.drawable.teeth_after);


                if(teeth4bool){
                    Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"상대방 탈락",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;
            }
            else if(i == 5 && teeth5after == false){
                Toast.makeText(getApplicationContext(),"상대방의 차례입니다.",Toast.LENGTH_SHORT).show();


                teeth5after = true;
                teeth5.setImageResource(R.drawable.teeth_after);


                if(teeth5bool){
                    Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"상대방 탈락",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;
            }
            else if(i == 6 && teeth6after == false){
                Toast.makeText(getApplicationContext(),"상대방의 차례입니다.",Toast.LENGTH_SHORT).show();


                teeth6after = true;
                teeth6.setImageResource(R.drawable.teeth_after);


                if(teeth6bool){
                    Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"상대방 탈락",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;
            }
        }
        turn = true;

    }


}
