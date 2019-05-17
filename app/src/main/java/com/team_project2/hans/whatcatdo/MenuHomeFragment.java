package com.team_project2.hans.whatcatdo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.team_project2.hans.whatcatdo.database.DBLogHelper;
import com.team_project2.hans.whatcatdo.database.LogEmotion;
import java.util.ArrayList;

public class MenuHomeFragment extends Fragment {

    SliderLayout sliderLayout;
    DBLogHelper db;
    ArrayList<LogEmotion> logs;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_home, container, false);

        db = new DBLogHelper(view.getContext());
        logs = db.getLogEmotion();

        sliderLayout = view.findViewById(R.id.slider_main);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.SLIDE);
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds

        setSliderViews();
        return view;
    }


    private void setSliderViews() {

        if(logs.isEmpty()){
            return;
        }
        for(LogEmotion log : logs){
            SliderView sliderView = new SliderView(view.getContext());
            Log.d("ss",log.getPath());
            sliderView.setImageUrl(log.getPath());
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(log.getComment());
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {

                }

            });
            sliderLayout.addSliderView(sliderView);

        }
    }

}
