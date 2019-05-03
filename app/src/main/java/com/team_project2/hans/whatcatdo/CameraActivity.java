package com.team_project2.hans.whatcatdo;

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

public class CameraActivity extends AppCompatActivity {
    private final String TAG = "CAMERA";

    private CameraView cameraView;
    private ImageView btn_record;
    private boolean isRecording = false;

    File video;

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

                if (isRecording) {
                    //cameraView.stopVideo();
                    cameraView.captureVideo();
                    Log.d(TAG,cameraView.getPreviewSize().toString());
                    Toast.makeText(CameraActivity.this, "분석 종료!", Toast.LENGTH_SHORT).show();
                    isRecording = false;
                    return;
                }
                Toast.makeText(CameraActivity.this, "분석 시작", Toast.LENGTH_SHORT).show();
                cameraView.captureVideo();
                isRecording = true;
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

    void setCamera(){
        cameraView.setVideoQuality(CameraKit.Constants.VIDEO_QUALITY_720P);

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
                video = cameraKitVideo.getVideoFile();
                Log.d(TAG+"2", String.valueOf(cameraKitVideo.getVideoFile()));
            }
        });

    }

}