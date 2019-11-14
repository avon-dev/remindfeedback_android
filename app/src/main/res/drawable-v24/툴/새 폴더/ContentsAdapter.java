package com.example.wanna_bet70;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContentsAdapter extends RecyclerView.Adapter<ContentsAdapter.ViewHolder> {
    //임시ArrayList<Person> items = new ArrayList<Person>();
    //임시OnPersonItemClickListener listener;
    private ArrayList<Contents> items;
    private Context mContext;




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contents_item, viewGroup, false);
        mContext = viewGroup.getContext();
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
       // viewHolder.contents_Name.setText(items.get(position).getName());
        //임시
        viewHolder.onBind(items.get(position));


    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public void addItem(Contents item) {
        items.add(item);
    }
    //위 두줄 임시

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private Button contents_Name;
        private Contents data;


        public ViewHolder(View itemView) {
            super(itemView);
           contents_Name = (Button) itemView.findViewById(R.id.contents_Name);



        }
        void onBind(Contents data) {
            this.data = data;
            contents_Name.setText(data.getName());

            contents_Name.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {//아이템들을 눌렀을때 실행되는 부분 아이템 추가되면 여기에 넣어줘야함
           String aaa = contents_Name.getText().toString();

            if(aaa.equals("사다리타기")){
                Intent intent = new Intent(mContext.getApplicationContext(), GameInfoActivity.class);
                intent.putExtra("gameintent", 1);
                mContext.startActivity(intent);
            }else if(aaa.equals("폭탄넘기기")){


                Intent intent = new Intent(mContext.getApplicationContext(), GameInfoActivity.class);
                intent.putExtra("gameintent", 2);
                mContext.startActivity(intent);
            }else if(aaa.equals("악어이빨")){


                Intent intent = new Intent(mContext.getApplicationContext(), GameInfoActivity.class);
                intent.putExtra("gameintent", 3);
                mContext.startActivity(intent);
            }
            else{
                Toast.makeText(mContext, "구현되지 않은 게임입니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public ContentsAdapter(Context context, ArrayList<Contents> list) {
        items = list;
        mContext = context;

    }






}
