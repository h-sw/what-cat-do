package com.team_project2.hans.whatcatdo.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_project2.hans.whatcatdo.CameraActivity;
import com.team_project2.hans.whatcatdo.ImageSelectActivity;
import com.team_project2.hans.whatcatdo.R;

public class MenuAnalyzeFragment extends Fragment {

    CardView card_menu_picture;
    CardView card_menu_camera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_analyze, container, false);

        card_menu_camera = view.findViewById(R.id.card_menu_camera);
        card_menu_picture = view.findViewById(R.id.card_menu_picture);

        card_menu_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CameraActivity.class));
            }
        });

        card_menu_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ImageSelectActivity.class));
            }
        });

        return view;
    }
}
