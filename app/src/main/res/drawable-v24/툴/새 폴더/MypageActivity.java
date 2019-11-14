package com.example.wanna_bet70;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MypageActivity extends AppCompatActivity {
    private static final String TAG = "WannaBet";
    private Boolean isPermission = true;

    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    private File tempFile;
    boolean profile = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        tedPermission();








        ImageView profileImage2 = (ImageView) findViewById(R.id.profileImage2);
        profileImage2.setImageResource(R.mipmap.ic_default_round);

        profileImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                builder.setMessage("프로필 사진 변경")

                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                profile = true;
                                AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                                builder.setMessage("프로필 사진 변경")


                                        .setCancelable(false)
                                        .setPositiveButton("앨범", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {


                                                if(isPermission) goToAlbum();
                                                 else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setNegativeButton("카메라", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(isPermission)  takePhoto();
                                                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();

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
                                profile = false;
                                dialog.dismiss();

                            }
                        });



                AlertDialog alert = builder.create();
                alert.setTitle("프로필");
                alert.show();



            }
        });

        Button betList_Button = (Button) findViewById(R.id.betList_Button);


        betList_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Intent intent = new Intent(getApplicationContext(), BetListActivity.class);
                startActivity(intent);

            }
        });



        ImageView profileImage = (ImageView) findViewById(R.id.profileImage);
        profileImage.setImageResource(R.mipmap.ic_launcher);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                builder.setMessage("배경 사진 변경")

                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                profile = false;
                                AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                                builder.setMessage("프로필 사진 변경")

                                        .setCancelable(false)
                                        .setPositiveButton("앨범", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {


                                                if(isPermission) goToAlbum();
                                                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setNegativeButton("카메라", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(isPermission)  takePhoto();
                                                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();

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
                                profile = false;
                                dialog.dismiss();

                            }
                        });



                AlertDialog alert = builder.create();
                alert.setTitle("프로필");
                alert.show();



            }
        });




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

        switch (requestCode) {
            case PICK_FROM_ALBUM: {

                Uri photoUri = data.getData();

                cropImage(photoUri);

                break;
            }
            case PICK_FROM_CAMERA: {

                Uri photoUri = Uri.fromFile(tempFile);

                cropImage(photoUri);

                break;
            }
            case Crop.REQUEST_CROP: {

                setImage();
            }

        }



    }


    private void cropImage(Uri photoUri) {//카메라 갤러리에서 가져온 사진을 크롭화면으로 보냄

        Log.d(TAG, "tempFile : " + tempFile);

        /**
         *  갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해줍니다.
         */
        if(tempFile == null) {
            try {
                tempFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        }

        //크롭 후 저장할 Uri
        Uri savingUri = Uri.fromFile(tempFile);//사진촬여은 tempFile이 만들어져있어 넣어서 저장하면됨
        //하지만 갤러리는 크롭후에 이미지를 저장할 파일이 없기에 위 코드를넣어서 추가로 작성해줘야함

        Crop.of(photoUri, savingUri).asSquare().start(this);
    }

    private void goToAlbum() {

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

    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "WannaBet_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/WannaBet_/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        return image;
    }

    private void setImage() {

        if( profile){
            ImageView profileImage2 = findViewById(R.id.profileImage2);

            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
            Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());


            int width=(int)(200); // 가로 사이즈 지정
            int height=(int)((width*1)/1); // 세로 사이즈 지정
            Bitmap resizedbitmap=Bitmap.createScaledBitmap(originalBm, width, height, true); // 이미지 사이즈 조정
            profileImage2.setImageBitmap(resizedbitmap); // 이미지뷰에 조정한 이미지 넣기
            /**
             *  tempFile 사용 후 null 처리를 해줘야 합니다.
             *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
             *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
             */
            tempFile = null;
        }
        else{
            ImageView profileImage = findViewById(R.id.profileImage);

            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);



            int width=(int)(650); // 가로 사이즈 지정
            int height=(int)((width*1)/1); // 세로 사이즈 지정
            Bitmap resizedbitmap=Bitmap.createScaledBitmap(originalBm, width, height, true); // 이미지 사이즈 조정
            profileImage.setImageBitmap(resizedbitmap); // 이미지뷰에 조정한 이미지 넣기
            /**
             *  tempFile 사용 후 null 처리를 해줘야 합니다.
             *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
             *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
             */
            tempFile = null;
        }



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
