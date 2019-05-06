package com.team_project2.hans.whatcatdo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Time;
import java.sql.Timestamp;

public class CameraActivity extends AppCompatActivity {
    private final String TAG = "CAMERA";

    private CameraView cameraView;
    private ImageView btn_record;
    private boolean isRecording = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();
        checkPermission();

        cameraView = findViewById(R.id.camera);
        btn_record = findViewById(R.id.btn_record);

        cameraView.setVideoBitRate(CameraKit.Constants.VIDEO_QUALITY_480P);

        Toast.makeText(this, "가운데 버튼을 눌러 고양이를 촬영하고 있으세요!", Toast.LENGTH_SHORT).show();


        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {
                Log.d("OutputVideo","onVideo called");
                if(cameraKitVideo.getVideoFile().exists()){
                    File file = cameraKitVideo.getVideoFile();

                    Log.d("OutputVideo",file.toString());
                    Log.d("OutputVideo",Long.toString(file.length()));

                    Toast.makeText(CameraActivity.this, "분석종료!"+file.toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CameraActivity.this,NewCameraActivity.class);
                    intent.putExtra("videoPath",file.getAbsolutePath());
                    startActivity(intent);


                }

            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraActivity.this, "분석 시작!", Toast.LENGTH_SHORT).show();
                cameraView.captureVideo();
                cameraView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cameraView.stopVideo();

                        Toast.makeText(CameraActivity.this, "분석 종료!", Toast.LENGTH_SHORT).show();
                    }
                },3000);
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
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

            } else {
                //Toast.makeText(this, "권한 승인되었음", Toast.LENGTH_SHORT).show();
            }
        }
    }


}