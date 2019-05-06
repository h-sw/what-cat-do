package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CardView card_camera;
    CardView card_log;
    CardView card_picture;


    private int MAX_ITEM_COUNT = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Loading();
        card_picture = findViewById(R.id.card_picture);
        card_camera = findViewById(R.id.card_camera);
        card_log = findViewById(R.id.card_log);

        card_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadActivity(v);
            }
        });
        card_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadActivity(v);
            }
        });
        card_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadActivity(v);
            }
        });

    }

    private void Loading(){
        startActivity(new Intent(this, LodingActivity.class));
    }

    public void LoadActivity(View view){
        if(view.getId()==card_camera.getId())
            startActivity(new Intent(this,CameraActivity.class));
        else if(view.getId()==card_log.getId())
            startActivity(new Intent(this,LogActivity.class));
        else if(view.getId()==card_picture.getId())
            startActivity(new Intent(this,ImageSelectActivity.class));
        else
            Toast.makeText(getApplicationContext(),"no Activity!",Toast.LENGTH_LONG);
    }
}
