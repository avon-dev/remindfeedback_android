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
import android.widget.Toast;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    //임시ArrayList<Person> items = new ArrayList<Person>();
    //임시OnPersonItemClickListener listener;
    private ArrayList<Person> items;
    private Context mContext;

    public void addItem(Person item) {
        items.add(item);
    }
    //위 두줄 임시

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{
        protected TextView person_Profile_Name;
        protected TextView person_Profile_Email;
        protected ImageView person_Profile_Image;

        public ViewHolder(View itemView) {
            super(itemView);


            this.person_Profile_Name = (TextView) itemView.findViewById(R.id.person_Profile_Name);
            this.person_Profile_Email = (TextView) itemView.findViewById(R.id.person_Profile_Email);
            this.person_Profile_Image = (ImageView) itemView.findViewById(R.id.person_Profile_Image);
            itemView.setOnCreateContextMenuListener(this);


        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 메뉴 추가U

            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);

        }



        // 4. 캔텍스트 메뉴 클릭시 동작을 설정
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()) {
                    case 1001:

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext)
                                .inflate(R.layout.edit_box, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                       // final ImageView person_Dialog_Image = (ImageView)view.findViewById(R.id.person_Dialog_Image);//이미지 선택해서 바꾸는거
                        final EditText editTextName = (EditText) view.findViewById(R.id.edittext_dialog_name);
                        final EditText editTextEmail = (EditText) view.findViewById(R.id.edittext_dialog_email);




                        editTextName.setText(items.get(getAdapterPosition()).getName());
                        editTextEmail.setText(items.get(getAdapterPosition()).getEmail());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                String strName = editTextName.getText().toString();
                                String strEmail = editTextEmail.getText().toString();
                                if(android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()){
                                    Person dict = new Person(strName, strEmail, 0);

                                    items.set(getAdapterPosition(), dict);
                                    notifyItemChanged(getAdapterPosition());

                                    dialog.dismiss();
                                }
                                else{
                                   //Toast.makeText(FriendsListActivity.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                                    // 여기 유효성 검사 어케함;;


                                }





                            }
                        });

                        dialog.show();

                        break;

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


    public PersonAdapter(Context context, ArrayList<Person> list) {
        items = list;
        mContext = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.person_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.person_Profile_Name.setText(items.get(position).getName());
        viewHolder.person_Profile_Email.setText(items.get(position).getEmail());
        viewHolder.person_Profile_Image.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
