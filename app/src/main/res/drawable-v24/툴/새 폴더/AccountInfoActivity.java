package com.example.wanna_bet70;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AccountInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);






    }

    protected void onStart() {

        super.onStart();

        /*
        TextView nameText=(TextView)findViewById(R.id.nameText);
        Intent intent = getIntent();
        nameText.setText(intent.getStringExtra("nameText").toString());

        TextView idText=(TextView)findViewById(R.id.idText);

        idText.setText(intent.getStringExtra("idText").toString());

        TextView emailText=(TextView)findViewById(R.id.emailText);

        emailText.setText(intent.getStringExtra("emailText").toString());

        TextView phoneText=(TextView)findViewById(R.id.phoneText);

        phoneText.setText(intent.getStringExtra("phoneText").toString());

*/
    }

}
