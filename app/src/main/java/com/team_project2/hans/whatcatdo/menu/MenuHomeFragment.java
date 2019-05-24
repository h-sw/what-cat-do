package com.team_project2.hans.whatcatdo.menu;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.team_project2.hans.whatcatdo.R;
import com.team_project2.hans.whatcatdo.database.LogDBManager;
import com.team_project2.hans.whatcatdo.database.LogEmotion;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuHomeFragment extends Fragment {

    LogDBManager db;

    SliderLayout sliderLayout;
    ArrayList<LogEmotion> logs;
    View view;

    TextView text_img_count;
    TextView text_storage_size;
    TextView text_remain_storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_home, container, false);


        text_img_count = view.findViewById(R.id.text_img_count);
        text_storage_size = view.findViewById(R.id.text_storage_size);
        text_remain_storage = view.findViewById(R.id.text_remain_storage);

        setInfomation();


        db = new LogDBManager(view.getContext());
        logs = db.getLogEmotion();

        sliderLayout = view.findViewById(R.id.slider_main);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.WORM);
        sliderLayout.setScrollTimeInSec(7);

        setSliderViews();
        return view;
    }

    void setInfomation(){
        File dir = new File(Environment.getDataDirectory().getAbsolutePath()+"/whatcatdo/");

        //String storage_size = getFileSize(dir.length());

        text_storage_size.setText(dir.length()+"");
/*
        File[] list = dir.listFiles();
        int count = 0;
        for(File file : list){
            if(file.isFile())
                count++;
        }*/

       // text_img_count.setText(count+"개");

        File file = new File(Environment.getDataDirectory().getAbsolutePath());
        String s = getFileSize(file.getFreeSpace());


        text_remain_storage.setText(s);
    }

    public String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    private void setSliderViews() {
        if(logs.isEmpty()) return;

        int count = 0;
        for(LogEmotion log : logs){
            SliderView sliderView = new SliderView(view.getContext());
            sliderView.setImageUrl(log.getPath());
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(log.getComment());
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {

                }
            });
            sliderLayout.addSliderView(sliderView);
            count++;
            if(count == 5){
                break;
            }
        }
    }

}
