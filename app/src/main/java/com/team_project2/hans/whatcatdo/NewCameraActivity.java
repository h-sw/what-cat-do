package com.team_project2.hans.whatcatdo;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NewCameraActivity extends AppCompatActivity {
    MediaMetadataRetriever mediaMetadataRetriever;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_camera);
        getSupportActionBar().hide();

        mediaMetadataRetriever = new MediaMetadataRetriever();

        VideoView videoView = findViewById(R.id.videoView);
        if(!getIntent().getStringExtra("videoPath").isEmpty()){
            String path = getIntent().getStringExtra("videoPath");
            videoView.setVideoURI(Uri.parse(path));
            videoView.start();

            mediaMetadataRetriever.setDataSource(path);
            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
            //SaveBitmapToJPG(bitmap, Environment.getExternalStorageState()+"/WhatCatDo","image"+nCount+"jpg");

            mediaMetadataRetriever.release();

        }
    }

    public void SaveBitmapToJPG(Bitmap bitmap, String strFilePath, String filename){

        File file = new File(strFilePath);

        if (!file.exists()) {
            file.mkdirs();
        }

        File fileItem = new File(strFilePath + filename);
        OutputStream outStream = null;

        try {
            fileItem.createNewFile();
            outStream = new FileOutputStream(fileItem);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
