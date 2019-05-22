package com.team_project2.hans.whatcatdo.log;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.team_project2.hans.whatcatdo.R;
import com.team_project2.hans.whatcatdo.database.DBLogHelper;
import com.team_project2.hans.whatcatdo.database.LogEmotion;

import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DBLogHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        getSupportActionBar().hide();
        db = new DBLogHelper(LogActivity.this);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(LogActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<LogEmotion> myDataset = db.getLogEmotion();
        mAdapter = new LogAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }
    
}

