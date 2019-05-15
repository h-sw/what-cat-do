package com.team_project2.hans.whatcatdo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN ACTIVITY";

    /*layout Component*/
    CardView card_menu_camera;
    CardView card_menu_log;
    CardView card_menu_picture;
    CardView card_menu_info;

    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Loading();
        checkPermission();

        card_menu_picture = findViewById(R.id.card_menu_picture);
        card_menu_camera = findViewById(R.id.card_menu_camera);
        card_menu_log = findViewById(R.id.card_menu_log);
        card_menu_info = findViewById(R.id.card_menu_info);

       // cameraView = findViewById(R.id.camera_main);

        card_menu_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadActivity(v);
            }
        });
        card_menu_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadActivity(v);
            }
        });
        card_menu_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadActivity(v);
            }
        });
        card_menu_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadActivity(v);
            }
        });



    }


    private void Loading(){
        startActivity(new Intent(this, LoadingActivity.class));
    }

    public void LoadActivity(View view){
        if(view.getId()==card_menu_camera.getId())
            startActivity(new Intent(this,CameraActivity.class));
        else if(view.getId()==card_menu_log.getId())
            startActivity(new Intent(this,LogActivity.class));
        else if(view.getId()==card_menu_picture.getId())
            startActivity(new Intent(this,ImageSelectActivity.class));
        else if(view.getId()== card_menu_info.getId())
            startActivity(new Intent(this,InfoActivity.class));
        else
            Toast.makeText(getApplicationContext(),"no Activity!",Toast.LENGTH_LONG);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 마시멜로우 버전과 같거나 이상이라면
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]
                                {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        2);  //마지막 인자는 체크해야될 권한 갯수
            }
        }
    }
}
