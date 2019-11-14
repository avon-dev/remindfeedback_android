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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>{
    private ArrayList<ChatList> items;
    private Context mContext;
    public void addItem(ChatList item) {
        items.add(item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{
        protected TextView chat_List_Name;
        protected TextView chat_List_Content;
        protected TextView chat_List_Time;
        protected ImageView chat_List_Image;

        public ViewHolder(View itemView) {
            super(itemView);


            this.chat_List_Name = (TextView) itemView.findViewById(R.id.chat_List_Name);
            this.chat_List_Content = (TextView) itemView.findViewById(R.id.chat_List_Content);
            this.chat_List_Time = (TextView) itemView.findViewById(R.id.chat_List_Time);
            this.chat_List_Image = (ImageView) itemView.findViewById(R.id.chat_List_Image);
            itemView.setOnCreateContextMenuListener(this);


        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 메뉴 추가U

            //MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
           // Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);

        }



        // 4. 캔텍스트 메뉴 클릭시 동작을 설정
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()) {


                    case 1002:

                        items.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), items.size());

                        break;

                }
                return true;
            }
        };







    }

    public ChatListAdapter(Context context, ArrayList<ChatList> list) {
        items = list;
        mContext = context;

    }

    @NonNull
    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.person_item, viewGroup, false);

        ChatListAdapter.ViewHolder viewHolder = new ChatListAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.chat_List_Name.setText(items.get(i).getName());
        viewHolder.chat_List_Content.setText(items.get(i).getContent());
        viewHolder.chat_List_Time.setText(items.get(i).getTime());
        viewHolder.chat_List_Image.setImageResource(items.get(i).getImage());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }




}
