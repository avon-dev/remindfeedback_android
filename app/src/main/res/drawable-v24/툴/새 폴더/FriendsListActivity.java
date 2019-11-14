package com.example.wanna_bet70;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FriendsListActivity extends AppCompatActivity {


    private static final String TAG = "WannaBet";
    private Boolean isPermission = true;//권한 받아왔는지?
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private File tempFile;
    //여기까지 이미지 불러오기위한 변수들

    ArrayList<Person> mArrayList;//임시
    RecyclerView recyclerView;
    PersonAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
       tedPermission();//권한 체크


        recyclerView = findViewById(R.id.recyclerView);
        mArrayList = new ArrayList<>();//임시

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new PersonAdapter(this, mArrayList);



        recyclerView.setAdapter(adapter);
//여기부터 임시 버튼클릭
        //친구추가 버튼 눌렀을 시
        Button friends_Plus_Button = (Button)findViewById(R.id.friends_Plus_Button);
        friends_Plus_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






/*
                if(isPermission) goToAlbum();//앨범 권한에 동의하지 않았을경우
                else Toast.makeText(v.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();

                if(isPermission)  takePhoto();// 카메라 권한에 동의하지 않았을경우
                else Toast.makeText(v.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
*/

                AlertDialog.Builder builder = new AlertDialog.Builder(FriendsListActivity.this);
                View view = LayoutInflater.from(FriendsListActivity.this)
                        .inflate(R.layout.edit_box, null, false);//에딧 박스 액티비티를 다이얼로그로 띄움
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                final EditText editTextName = (EditText) view.findViewById(R.id.edittext_dialog_name);
                final EditText editTextEmail = (EditText) view.findViewById(R.id.edittext_dialog_email);
                //버튼과 에딧텍스트들을 설정

                final ImageView person_Dialog_Image = (ImageView)view.findViewById(R.id.person_Dialog_Image);

               // Toast.makeText(FriendsListActivity.this, "에바쎄바", Toast.LENGTH_SHORT).show();


                person_Dialog_Image.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(FriendsListActivity.this);
                        builder.setMessage("프로필 사진 변경")

                                .setCancelable(false)
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(FriendsListActivity.this);
                                        builder.setMessage("프로필 사진 변경")


                                                .setCancelable(false)
                                                .setPositiveButton("앨범", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {


                                                        if(isPermission) goToAlbum();
                                                        // else Toast.makeText(v.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .setNegativeButton("카메라", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if(isPermission)  takePhoto();
                                                        //else Toast.makeText(v.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();

                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.setTitle("프로필");
                                        alert.show();

                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();

                                    }
                                });



                        AlertDialog alert = builder.create();
                        alert.setTitle("프로필");
                        alert.show();
                    }
                });


                ButtonSubmit.setText("삽입");


                final AlertDialog dialog = builder.create();

                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {


                        String strName = editTextName.getText().toString();
                        String strEmail = editTextEmail.getText().toString();
                       //int Imageimage = dialog_Image;
                        if(android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())
                        {


                            Person dict = new Person(strName, strEmail, 0);//여기다가 이미지값을 넣어줘야 하는데 어떻게하는지...

                            //mArrayList.add(0, dict); //첫 줄에 삽입
                            mArrayList.add(dict); //마지막 줄에 삽입
                            adapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

                            dialog.dismiss();
                            return;
                        }
                        else{
                            Toast.makeText(FriendsListActivity.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();


                        }


                    }
                });

                dialog.show();
            }
        });
//여기까지 임시 버튼클릭

        adapter.addItem(new Person("김민수", "xxx@naver.com", R.drawable.snoopy));
        adapter.addItem(new Person("김하늘", "xxx@naver.com", R.drawable.duck));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.lion));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.ggobuk));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.bomb));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.snoopy));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.duck));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.lion));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.ggobuk));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.snoopy));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.duck));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.lion));
        adapter.addItem(new Person("홍길동", "xxx@naver.com", R.drawable.ggobuk));
        //안에 하드코딩해놓은 것들



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();
            Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

                Log.d(TAG, "tempFile Uri : " + Uri.fromFile(tempFile));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        } else if (requestCode == PICK_FROM_CAMERA) {

            setImage();

        }

    }


    private void goToAlbum(){//앨범에서 이미지 가져오기

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            Uri photoUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private File createImageFile() throws IOException {//폴더 및 파일 만들기

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        return image;
    }

    private void setImage() {

        ImageView person_Dialog_Image = findViewById(R.id.person_Dialog_Image);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());

        int width=(int)(200); // 가로 사이즈 지정
        int height=(int)((width*1)/1); // 세로 사이즈 지정
        Bitmap resizedbitmap=Bitmap.createScaledBitmap(originalBm, width, height, true); // 이미지 사이즈 조정
        person_Dialog_Image.setImageBitmap(resizedbitmap); // 이미지뷰에 조정한 이미지 넣기 여기서 졸라 에러뜸 ㅜㅠ

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        tempFile = null;

    }

    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }






}

