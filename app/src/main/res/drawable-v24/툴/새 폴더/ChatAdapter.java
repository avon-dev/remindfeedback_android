package com.example.wanna_bet70;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatMe> items;
    private Context mContext;

    boolean check = false;//나와 상대 바꾸는 거

    /*
    private static final int VIEW_TYPE1 = 1;
    private static final int VIEW_TYPE2 = 2;
*/

    @Override
    public int getItemViewType(int position) {//여기서 뷰타입을 1, 2로 바꿔서 지정해줘야 내채팅 너채팅을 바꾸면서 쌓을 수 있음
//디폴트값이 fasle라 처음에는 뷰타입1로 시작함


        /*
        if(check == false){
            return 1;
        }else{
            return 2;
        }
        */
        if (items.get(position).getViewtype() == 1) {
            return 1;
        } else {
            return 2;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
//뷰타입1 -> 뷰홀더 1, 뷰타입2 -> 뷰홀더2
        if(viewType == 1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_me_item, parent, false);
            ViewHolder1 vh = new ViewHolder1(v);
            return  vh;
        }

        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_you_item, parent, false);
            ViewHolder2 vh = new ViewHolder2(v);
            return  vh;
        }

    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        //ChatMe chatme = items.get(i);
//뷰홀더가 뷰홀더1의 인스턴스이면 뷰횰더1 안의 두 텍스트뷰를 각각 셋한다.
        if(viewHolder instanceof ViewHolder1){
            ((ViewHolder1) viewHolder).chat_Me_Text.setText(items.get(i).getChatText());
            ((ViewHolder1) viewHolder).chat_Me_Time.setText(items.get(i).getChatTime());
        }
        else{
            ((ViewHolder2) viewHolder).chat_You_Text.setText(items.get(i).getChatText());
            ((ViewHolder2) viewHolder).chat_You_Time.setText(items.get(i).getChatTime());
        }


    }

/*
    private  ChatMe getItem(int position){
        return items.get(position);
    }

    private void setItems(ArrayList<ChatMe> list){
        items.clear();
        items.addAll(items);
    }
*/

    //아이템 추가
    public void addItem(ChatMe item) {
        items.add(item);
        //notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return items.size();//리스트 사이즈만큼 크기 할당
    }


    //내 채팅 뷰홀더
    public class ViewHolder1 extends RecyclerView.ViewHolder  implements  View.OnCreateContextMenuListener{
        protected TextView chat_Me_Text;
        protected TextView chat_Me_Time;


        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            this.chat_Me_Text = (TextView) itemView.findViewById(R.id.chat_Me_Text);
            this.chat_Me_Time = (TextView) itemView.findViewById(R.id.chat_Me_Time);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 메뉴 추가U


            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);

        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                items.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), items.size());
                return true;
            }
        };


    }
    //상대 채팅팅 뷰홀
    public class ViewHolder2 extends RecyclerView.ViewHolder  implements  View.OnCreateContextMenuListener{

        protected TextView chat_You_Text;
        protected TextView chat_You_Time;

        public ViewHolder2(@NonNull View itemView) {

            super(itemView);
            this.chat_You_Text = (TextView) itemView.findViewById(R.id.chat_You_Text);
            this.chat_You_Time = (TextView) itemView.findViewById(R.id.chat_You_Time);
            itemView.setOnCreateContextMenuListener(this);
        }
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 메뉴 추가U


            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);

        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                items.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), items.size());
                return true;
            }
        };


    }



    public ChatAdapter(Context context, ArrayList<ChatMe> list) {

        items = list;
        mContext = context;

    }








}
