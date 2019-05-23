package com.team_project2.hans.whatcatdo.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team_project2.hans.whatcatdo.CameraActivity;
import com.team_project2.hans.whatcatdo.ImageSelectActivity;
import com.team_project2.hans.whatcatdo.log.LogAdapter;
import com.team_project2.hans.whatcatdo.R;
import com.team_project2.hans.whatcatdo.database.LogDBManager;
import com.team_project2.hans.whatcatdo.database.LogEmotion;

import java.util.ArrayList;


public class MenuLogFragment extends Fragment{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LogDBManager db;
    LogAdapter mAdapter;
    LinearLayout emptyView;

    TextView text_empty_gallary;
    TextView text_empty_camera;
    ArrayList<LogEmotion> myDataset;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu_log, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        emptyView = view.findViewById(R.id.empty_view);
        text_empty_camera = view.findViewById(R.id.text_empty_camera);
        text_empty_gallary = view.findViewById(R.id.text_empty_gallary);


        text_empty_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ImageSelectActivity.class));
            }
        });

        text_empty_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CameraActivity.class));
            }
        });

        return view;
    }

    private class LogManager extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(view.getContext());

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중이에요~");
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                db = new LogDBManager(getContext());
                myDataset = db.getLogEmotion();

            } catch (Exception e) {
                e.printStackTrace();
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!myDataset.isEmpty()) {
                        mAdapter.setDataSet(myDataset);
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new LogManager().execute();
            recyclerView.setHasFixedSize(true);

            mAdapter = new LogAdapter(null);

            // use a linear layout manager
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(mAdapter);

        }
    }
}
