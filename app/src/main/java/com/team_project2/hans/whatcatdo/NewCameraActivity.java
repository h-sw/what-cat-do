package com.team_project2.hans.whatcatdo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class NewCameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_camera);

        VideoView videoView = findViewById(R.id.videoView);
        if(!getIntent().getStringExtra("videoPath").isEmpty()){
            videoView.setVideoURI(Uri.parse(getIntent().getStringExtra("videoPath")));
            videoView.start();
        }



    }


}
