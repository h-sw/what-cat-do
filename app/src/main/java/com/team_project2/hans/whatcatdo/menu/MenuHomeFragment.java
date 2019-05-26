package com.team_project2.hans.whatcatdo.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.team_project2.hans.whatcatdo.CameraActivity;
import com.team_project2.hans.whatcatdo.ImageSelectActivity;
import com.team_project2.hans.whatcatdo.R;
import com.team_project2.hans.whatcatdo.database.LogDBManager;
import com.team_project2.hans.whatcatdo.database.LogEmotion;
import com.team_project2.hans.whatcatdo.info.InfoActivity;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuHomeFragment extends Fragment {

    LogDBManager db;

    SliderLayout sliderLayout;
    ArrayList<LogEmotion> logs;
    View view;
    CardView go_Info;
    TextView text_img_count;
    TextView text_storage_size;
    TextView text_remain_storage;

    CardView card_fast_camera;
    CardView card_fast_image;
    CardView card_delete_cache;

    LinearLayout no_log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_home, container, false);

        go_Info = view.findViewById(R.id.cv_goInfo);

        text_img_count = view.findViewById(R.id.text_img_count);
        text_storage_size = view.findViewById(R.id.text_storage_size);
        text_remain_storage = view.findViewById(R.id.text_remain_storage);

        card_fast_camera = view.findViewById(R.id.card_fast_camera);
        card_fast_image = view.findViewById(R.id.card_fast_image);
        card_delete_cache = view.findViewById(R.id.card_delete_cache);
        no_log = view.findViewById(R.id.no_log);


        go_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InfoActivity.class));
            }
        });
        setInfomation();

        card_fast_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ImageSelectActivity.class));
            }
        });

        card_fast_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CameraActivity.class));
            }
        });

        card_delete_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCache();
                onResume();
            }
        });


        db = new LogDBManager(view.getContext());
        logs = db.getLogEmotion();

        sliderLayout = view.findViewById(R.id.slider_main);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.WORM);
        sliderLayout.setScrollTimeInSec(7);

        setSliderViews();
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setInfomation();
            }
        });

    }

    private void deleteCache() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/whatcatdo");

        File[] list = dir.listFiles();

        for(File file : list){
            boolean isLog = false;
            for(LogEmotion emotion : logs){
                Log.d("sss1", file.getAbsolutePath());
                Log.d("sss2", emotion.getPath());
                if(file.getAbsolutePath().equals(emotion.getPath()))
                    isLog = true;
            }
            if(!isLog)
                file.delete();
        }
        Toast.makeText(getContext(), "파일 정리에 성공하였습니다!", Toast.LENGTH_SHORT).show();
    }

    void setInfomation(){
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/whatcatdo");
        long size = 0;

        File[] list = dir.listFiles();
        if(list!=null){
            int count = 0;
            for(File file : list){
                if(file.isFile()){
                    count++;
                    size += file.length();
                }
            }
            text_storage_size.setText(getFileSize(size));
            text_img_count.setText(count+"장");
        }

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
        if(logs.isEmpty()){
            no_log.setVisibility(view.VISIBLE);
            sliderLayout.setVisibility(view.GONE);
            return;
        }

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
