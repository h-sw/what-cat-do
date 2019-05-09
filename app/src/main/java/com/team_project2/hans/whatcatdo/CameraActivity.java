package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG         = "CAMERA ACTIVITY";

    private static final int    RECORD_TIME = 3000;

    /*layout Component*/
    private CameraView cameraView;
    private ImageView btn_record;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();

        cameraView = findViewById(R.id.camera);
        btn_record = findViewById(R.id.btn_record);

        setCamera();

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideo();
            }
        });

        Toast.makeText(this, "가운데 버튼을 눌러 고양이를 촬영하고 있으세요!", Toast.LENGTH_SHORT).show();
    }

    void recordVideo(){
        Toast.makeText(CameraActivity.this, "분석 시작!", Toast.LENGTH_SHORT).show();
        cameraView.captureVideo();
        cameraView.postDelayed(new Runnable() {
            @Override
            public void run() {
                cameraView.stopVideo();
            }
        },RECORD_TIME);
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

    void setCamera(){
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
                if(cameraKitVideo.getVideoFile().exists()){
                    Toast.makeText(CameraActivity.this, "분석 종료!", Toast.LENGTH_SHORT).show();
                    File file = cameraKitVideo.getVideoFile();
                    Intent intent = new Intent(CameraActivity.this, CameraResultActivity.class);
                    intent.putExtra("videoPath",file.getAbsolutePath());
                    startActivity(intent);
                }

            }
        });
    }
}