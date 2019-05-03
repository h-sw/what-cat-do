package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class ImageSelectActivity extends AppCompatActivity {
    private ImageView img_select;
    private Button btn_select;
    private Button btn_analyze_picture;

    private boolean isSelect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);

        img_select = findViewById(R.id.img_select);
        btn_select = findViewById(R.id.btn_select);
        btn_analyze_picture = findViewById(R.id.btn_analyze_picture);


        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });

        btn_analyze_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isSelect){

               }else{
                   Toast.makeText(ImageSelectActivity.this, "이미지를 먼저 선택해 주세요!", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                try{
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    img_select.setImageBitmap(img);
                    isSelect = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
