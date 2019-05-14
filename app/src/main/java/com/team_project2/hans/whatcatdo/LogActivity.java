package com.team_project2.hans.whatcatdo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        db = new DBLogHelper(this);
        ArrayList<LogEmotion> myDataset = db.getLogEmotion();

        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        mAdapter = new LogAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }
}

