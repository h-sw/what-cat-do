package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CardView card_camera;
    CardView card_log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Loading();
        card_camera = findViewById(R.id.card_camera);
        card_log = findViewById(R.id.card_log);

        card_camera.setOnClickListener(new View.OnClickListener() {
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
            startActivity(new Intent(this,CameraActivity.class));
        else
            Toast.makeText(getApplicationContext(),"no Activity!",Toast.LENGTH_LONG);

    }
}
