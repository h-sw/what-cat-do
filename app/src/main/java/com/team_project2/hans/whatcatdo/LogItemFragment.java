package com.team_project2.hans.whatcatdo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class LogItemFragment extends Fragment {
    private View view;
    private CardView card_log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_log, container, false);
        card_log = view.findViewById(R.id.card_log);

        Log.d("dd","S--------");
        card_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "ㅁㅁㅁ", Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_log, container, false);
    }

}
