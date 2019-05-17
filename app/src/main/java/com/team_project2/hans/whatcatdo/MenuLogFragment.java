package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team_project2.hans.whatcatdo.database.DBLogHelper;
import com.team_project2.hans.whatcatdo.database.LogEmotion;

import java.util.ArrayList;


public class MenuLogFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DBLogHelper db;
    LogAdapter mAdapter;
    LinearLayout emptyView;
    TextView text_empty_gallary;
    TextView text_empty_camera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_log, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        emptyView = view.findViewById(R.id.empty_view);
        text_empty_camera = view.findViewById(R.id.text_empty_camera);
        text_empty_gallary = view.findViewById(R.id.text_empty_gallary);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                db = new DBLogHelper(getContext());
                ArrayList<LogEmotion> myDataset = db.getLogEmotion();
                if(!myDataset.isEmpty()) {
                    mAdapter = new LogAdapter(myDataset);
                    recyclerView.setAdapter(mAdapter);

                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }else{
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        });

        text_empty_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ImageSelectActivity.class));
            }
        });

        text_empty_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),CameraActivity.class));
            }
        });

        return view;
    }
}
