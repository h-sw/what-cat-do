package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;

import static com.team_project2.hans.whatcatdo.common.Common.RECORD_TIME;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG         = "CAMERA ACTIVITY"; //보기 편리하게 태그

    /*layout Component*/
    private CameraView cameraView;
    private ImageView btn_record;
    private TextView text_camera_how;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();

        cameraView = findViewById(R.id.camera);
        btn_record = findViewById(R.id.btn_record);
        text_camera_how = findViewById(R.id.text_camera_how);

        setCamera();

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideo();
                btn_record.setClickable(false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text_camera_how.setText("촬영중 입니다. 잠시만 기다리세요.");
                    }
                });
            }
        });

        Animation animation = AnimationUtils.loadAnimation(CameraActivity.this,R.anim.blink_animation);
        text_camera_how.startAnimation(animation);

        Toast.makeText(this, "가운데 버튼을 눌러 고양이를 촬영하세요", Toast.LENGTH_SHORT).show();
    }

    void recordVideo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CameraActivity.this, "분석 시작", Toast.LENGTH_SHORT).show();
                cameraView.captureVideo();
                cameraView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cameraView.stopVideo();
                    }
                },RECORD_TIME);
            }
        }).run();

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
            public void onEvent(CameraKitEvent cameraKitEvent) { }
            @Override
            public void onError(CameraKitError cameraKitError) { }
            @Override
            public void onImage(CameraKitImage cameraKitImage) { }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {
                final CameraKitVideo video = cameraKitVideo;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(video.getVideoFile().exists()){
                            Toast.makeText(CameraActivity.this, "촬영 종료", Toast.LENGTH_SHORT).show();
                            File file = video.getVideoFile();
                            Intent intent = new Intent(CameraActivity.this, CameraResultActivity.class);
                            intent.putExtra("videoPath",file.getAbsolutePath());
                            startActivity(intent);
                            finish();
                        }
                    }
                }).run();
            }
        });
    }
}